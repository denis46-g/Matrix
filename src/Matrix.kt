@file:Suppress("UNUSED_PARAMETER")

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E>{
    if(height <= 0 || width <= 0)
        throw IllegalArgumentException("размеры матрицы введены некорректно.")
    val res = MatrixImpl<E>(height, width, e)
    return res
}

/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>(private val h: Int, private val w: Int, private val e: E) : Matrix<E> {

    private val data = MutableList(h) { MutableList(w) { e } }

    override val height: Int = data.size

    override val width: Int = data[0].size

    override fun get(row: Int, column: Int): E {
        if (row < 0 || row >= height || column < 0 || column >= width)
            throw IllegalArgumentException("введённый(ые) индекс(ы) вне размеров матрицы.")
        return data[row][column]
    }

    override fun get(cell: Cell): E {
        if (cell.row < 0 || cell.row >= height || cell.column < 0 || cell.column >= width)
            throw IllegalArgumentException("введённый(ые) индекс(ы) вне размеров матрицы.")
        return data[cell.row][cell.column]
    }

    override fun set(row: Int, column: Int, value: E) {
        if (row < 0 || row >= height || column < 0 || column >= width)
            throw IllegalArgumentException("введённый(ые) индекс(ы) вне размеров матрицы")
        data[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        if (cell.row < 0 || cell.row >= height || cell.column < 0 || cell.column >= width)
            throw IllegalArgumentException("введённый(ые) индекс(ы) вне размеров матрицы")
        data[cell.row][cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if(other !is MatrixImpl<*> || other.height != height || other.width != width)
            return false
        for (i in 0..< height)
            for (j in 0..< width)
                if (data[i][j] != other[i,j])
                    return false
        return true
    }

    override fun toString(): String {
        var str = ""
        for (i in 0..< height){
            for (j in 0..< width)
                str += data[i][j].toString() + ' '
            str += '\n'
        }
        return str
    }
}
