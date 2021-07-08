docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

listeners.tcp.default = 5672
management.tcp.port = 15672

http://localhost:15672/
login:guest
passw:guest