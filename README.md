# RESTClientServerExample
Проект - пример REST сервера на платформе JAVA EE и клиектского приложения c использованием GUI JavaFX.

Идея проекта в том, что у пользователя имеется банковское приложение позволяющее производить переводы между счетами.
Приложение представляет из себя два модуля.

Первый модуль - это RESTServer представляет из себя серверную часть, работающую на базе сервера приложений GlassFish 4.
Серверная часть взаимодействует с сервером БД с помощью: сервера GalssFish, JPA и модулей Java Beans для реализации DAO.
На базе серверной части реализован Rest сервис обрабатывающий запросы пользовательского приложения. 
Данные между клиентом и сервером передаются в формате Json.

Второй модуль - RESTClientJavaFX представляет из себя UI, предоставляющий пользователю следующие возможности:
-найти счет по номеру;
-просмотр операций;
-перевод средств с одного счета на другой;
-счет не может иметь отрицательный баланс;
-предполагается, что пользователю доступны операции по всем счетам.

При открытии первом запуске клиентского приложения потребуется внести адрес REST сервера (первый модуль). После того как соединение будет успешно установлено, приложение сохранит данные в реестр для дальнейшей работы. В главном окне приложения предлагается выбрать одного из нескольких пользователей.

Пользователям предоставляется доступ на просмотр только своих счетов и отправку средств на другие счета. Администратор имеет возможность просматривать все счета в системе. 

Можно добавить счет используя кнопку “Добавить счет”, после чего указав необходимые данные счет будет успешно добавлен в список.
Выбрав элемент из списка, можно приступить к просмотру операций над счетом.

Доступен просмотр операций выполняемых как при переводе "Выписка по отправке" так и при получении средств "Выписка по получению".

Для выполнения перевода необходимо нажать на кнопку "Перевод".
Выполняя перевод необходимо удостовериться, что счет действительно существует в системе. Для этого при введении предполагаемого номера счета система автоматически выдает подсказки с похожими счетами, что позволяет нам быстро найти интересующий счет.

После отправки перевода система добавит информацию о переводе в список.

При выполнении проекта было использовано СУБД MySql.
Cущности: Users – таблица пользователей, BankAccount – таблица данных о счетах, BankAccountLink - связующая таблица для разграничения доступа пользователей к счетам, Billing – таблица с информацией об операциях с счетами, Operations – виды операций над счетами.

Для работы серверного ПО требуется настроить у сервера Glassfish был настроен новый Connection Pool с корректными параметрами подключения к базе данных. Данный ConnectionPool указать как пул по умолчанию в разделе “JDBC Resources -> jdbc/__default” в поле “Pool Name”.
