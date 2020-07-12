# Squad_reader

Пример работы программы:
В начале выводится в консоль путь до файла с результатами работы программы. Путь можно прописать вручную, подав его вторым аргументом (если этого не сделать, то файл создасться в дефолтной дирректории с указанием пути).
Первым аргументом указывается путь до файла с сотрудниками.

Далее идет построчное считывание файла и вывод в консоль.
Если строка имела некорректную запись, то в консоли выводится ошибка, номер строки и сама строка, с которой возникла проблема.
На скриншоте видно несколько таких моментов.

 ![Image alt](screen1.png)
 
 Программа в реальном времени записывает новых сотрудников и распределяет их по отделам/создает новые отделы, а по окончании работы запрашивает у каждого отдела информацию о содержимом (Название отдела, Сотрудники, заработок суммарный и каждого в частности, средний доход отдела)
 
 ![Image alt](screen2.png)
 
 В конце, в консоль выводится информация о возможных переводах сотрудников между отделами, при условии, что средний доход вырастет у обоих отделов.
 Эта информация выводится как в консоль, так и записывается в файл после каждого найденного решения.
 
 ![Image alt](screen3.png)

