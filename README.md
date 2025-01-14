# FileContentFilteringUtility

## Требования

Java: 17 

Gradle: 8.5 

Зависимости: Нет сторонних библиотек, только стандартные библиотеки Java.

## Инструкция по запуску

Клонирование репозитория:

```
git clone https://github.com/lana-svt/FileContentFilteringUtility.git
cd ./FileContentFilteringUtility/FilteringUtility
```

Сборка проекта с помощью Gradle:

```
./gradlew build
```

JAR-файл создасться в директории build/libs.

Пример запуска программы:

```
java -jar build/libs/FilteringUtility-1.0.jar -s -a -p sample- in1.txt in2.txt
```

## Параметры командной строки
-s: Вывод краткой статистики.

-f: Вывод полной статистики.

-a: Добавить в существующие выходные файлы, если они уже существуют, иначе вызовется исключение.

-p: Префикс для имен выходных файлов.

-o: Задать путь для результатов.

## Описание работы программы

Программа выполняет фильтрацию и статистический анализ данных из входных файлов. Она разбивает строки в файлах на три категории:

* Целые числа (long)

* Дробные числа (double)

* Строки

Результаты анализа сохраняются в файлы(если не добавляется префикса с помощью параметра -p):

* integers.txt — для целых чисел.

* floats.txt — для дробных чисел.

* strings.txt — для строк.