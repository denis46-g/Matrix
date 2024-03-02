@file:Suppress("UNUSED_PARAMETER")

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0..<matrix.width) {
        for (j in 0..<matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0..<matrix.width) {
        for (j in 0..<matrix.height) {
            result[i, j] = matrix[matrix.height - 1 - j, i]
        }
    }
    return result
}

/**
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if(height != other.height || width != other.width)
        throw IllegalArgumentException("размеры матриц не совпадают.")
    val result = createMatrix(height = height, width = width, e = this[0, 0])
    for (i in 0..<height) {
        for (j in 0..<width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int>{
    val result = createMatrix(height = height, width = width, e = this[0, 0])
    for (i in 0..<height) {
        for (j in 0..<width) {
            result[i, j] = -this[i,j]
        }
    }
    return result
}

/**
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    if(width != other.height)
        throw IllegalArgumentException("матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы..")
    val result = createMatrix(height = height, width = other.width, e = 0)
    for (i in 0..<result.height)
        for (j in 0..<result.width)
            for ( k in 0..<width)
                result[i, j] += this[i, k]*other[k, j]
    return result
}


/**
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val holeRows : MutableList<Int> = mutableListOf()
    val holeColumns : MutableList<Int> = mutableListOf()
    for (i in 0..<matrix.height)
    {
        var flag = true
        for (j in 0..<matrix.width)
            if(matrix[i, j] != 0)
            {
                flag = false
                break
            }
        if(flag)
            holeRows += i
    }
    for (j in 0..<matrix.width)
    {
        var flag = true
        for (i in 0..<matrix.height)
            if(matrix[i, j] != 0)
            {
                flag = false
                break
            }
        if(flag)
            holeColumns += j
    }
    return Holes(holeRows, holeColumns)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    if (key.height > lock.height || key.width > lock.width)
        return Triple<Boolean, Int, Int>(false, -1, -1)
    for (i in 0..lock.height - key.height)
        for (j in 0..lock.width - key.width){
            var flag = true
            for(ki in i..<i + key.height)
            {
                for(kj in j..<j + key.width) {
                    if (lock[ki, kj] == key[ki - i, kj - j]) {
                        flag = false
                        break
                    }
                }
                if(!flag)
                    break
            }
            if(flag)
                return Triple<Boolean, Int, Int>(true, i, j)
        }
    return Triple<Boolean, Int, Int>(false, -1, -1)
}