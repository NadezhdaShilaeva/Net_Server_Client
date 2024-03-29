# Net Server Client
###### Мини-проект эхо клиент-сервер

Данный мини-проет написан в качестве последнего практического задания по курсу:

[Разработка веб сервиса на Java (часть 2)](https://stepik.org/course/186)

В программе реализовано приложение эхо-сервер, который возвращает клиенту то, что тот ему написал. Сервер может работать с любым числом клиентов. Количество клинтов определяется константой ***CLIENTS_NUMBER*** (по умолчанию $20$). 

Сервер слушает на порту, задаваемом в первом аргументе при запуске (по умолчанию $5060$). При соединении с клиентом для работы с ним выделяется отдельный поток (то есть один поток на каждый сокет). Таким образом, сервер общается с каждым своим клиентом одновременно. Сервер получает сообщение от клиента целиком и посылает его обратно. Сигналом окончания взаимодействия и закрытия соединения (клиентского сокета) служит сообщение пользователя "Bye.".

Каждый клиент стартует в отдельном потоке, выделенном из пула потоков. Количество потоков в пуле определяется константой ***THREADS_NUMBER*** (по умолчанию $10$). Клиент пытается подключиться к серверу на порт, задаваемый в качестве первого аргумента (по умолчанию $5060$). Когда клиент подключается к серверу, то он начинает отправлять серверу сообщения в колличестве ***messagesNumber*** (по умолчанию $50$) с периодчностью ***sleepTimeMs*** (по умолчанию $100 ms$). Последним сообщением и сигналом на закрытие сокета является сообщение "Bye.".

Для запуска ***сервера*** необходимо выполнить команду:

```
java -Dlog4j.configurationFile=config/log4j2.xml -jar server.jar [port]
```


Для запуска ***клиента*** необходимо выполнить команду:

```
java -Dlog4j.configurationFile=config/log4j2.xml -jar client.jar [port]
```