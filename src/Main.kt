fun main() {

    //тестирование создания матрицы
    try{
        println("Тестирование создания матрицы")
        val m1 = createMatrix(2,3,777)
        println("Высота матрицы = " + m1.height)
        println("Ширина матрицы = " + m1.width)
        //вывод строкового представления матрицы в консоль
        println("Вывод строкового представления матрицы в консоль")
        println(m1.toString())
        println()
        //сетеры и геттеры
        println("Сеттеры и геттеры")
        m1[1,1] = 555
        m1[Cell(0,2)] = 888
        println(m1[1,1])
        println()
        println(m1[Cell(0,2)])
        println()
    } catch(e:IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    //сравнение матриц на равенство
    try{
        println("Сравнение матриц на равенство")
        val m2 = createMatrix(2,3,777)
        val m3 = createMatrix(2,3,777)
        println(m3 == m2)
        println()
        m3[1,2] = 333
        println(m3 == m2)
        println()
    } catch (e: IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    //операции с матрицами
    println("Операции с матрицами")

    try{
        println("1. Транспонирование")
        var m4 = createMatrix(2,3,777)
        m4[0, 1] = 555
        println(m4.toString())
        println()
        m4 = transpose(m4)
        println(m4.toString())
        println()
    }
    catch (e:IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    try{
        println("2. Rotation")
        var m5 = createMatrix(3,2, 0)
        m5[0,0] = 2
        m5[0,1] = 3
        m5[1,0] = 4
        m5[1,1] = 6
        m5[2,0] = 7
        m5[2,1] = 8
        println(m5.toString())
        println()
        m5 = rotate(m5)
        println(m5.toString())
        println()
    }
    catch (e:IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    try{
        println("3. Сложение матриц")
        val m6 = createMatrix(3,2, 777)
        m6[2, 0] = 555
        println("Первая матрица")
        println(m6.toString())
        println()
        val m7 = createMatrix(3,2, 777)
        m7[2, 0] = -555
        println("Вторая матрица")
        println(m7.toString())
        println()
        println("Сумма")
        println((m6 + m7).toString())
        println()
    } catch(e:IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    try{
        println("4. Получение противоположной матрицы")
        var m8 = createMatrix(3,2,777)
        println("Исходная матрица")
        println(m8.toString())
        println()
        m8 = -m8
        println("Противоположная к исходной матрица")
        println(m8.toString())
        println()
    } catch(e:IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    try{
        println("3. Произведение матриц")
        val m9 = createMatrix(2,3,5)
        m9[0,1] = 3
        println("Первая матрица")
        println(m9.toString())
        println()
        val m10 = createMatrix(3,4,7)
        m10[2,3] = 1
        println("Вторая матрица")
        print(m10.toString())
        println()
        println("Произведение матриц")
        print((m9 * m10).toString())
        println()
    } catch(e: IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    try{
        println("5. Нахождение в матрице строк и столбцов с дырками")
        val m11 = createMatrix(5,4,0)
        m11[0,0] = 1
        m11[0,2] = 1
        m11[1,2] = 1
        m11[2,0] = 1
        println("Матрица")
        println(m11.toString())
        println()
        println("Строки и столбцы с дырками:")
        println(findHoles(m11))
        println()
    } catch(e: IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }

    try{
        println("6. Подойдёт или не подойдёт ключ к замку")
        val lock = createMatrix(3,3, 1)
        lock[1,1] = 0
        lock[2,2] = 0
        val key = createMatrix(2,2, 1)
        key[1,0] = 0
        key[0,1] = 0
        println("Ключ:")
        println(key.toString())
        println()
        println("Замок:")
        println(lock.toString())
        println()
        print(canOpenLock(key, lock))
        println()
    }
    catch(e: IllegalArgumentException){
        println("Ошибка: ${e.message}")
    }
}