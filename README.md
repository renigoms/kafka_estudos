# Estudos com apache Kafka e docker compose com Spring Boot e Java

Este repositório foi idealizado com o proposito de abrigar meu progresso durante os estudo no curso Apache Kafka do Prof. Valdir Cesar na Udemy.
O curso é excelente e tem me ajudado a entender melhor o funcionamento do Kafka, além de me proporcionar uma experiência prática com Docker Compose, Spring Boot e Java.

## Docker Hub

* Dois dos projetos que eu fiz no curso estão com imagens no docker hub, eles seguem a lógica do produtor e consumidor, onde o produtor é responsável por enviar mensagens para o Kafka e o consumidor é responsável por ler essas mensagens. OS links para as imagens são:

    * [Json-Consumer](https://hub.docker.com/repository/docker/renigoms/json-consumer/general)
    * [Payment-Service](https://hub.docker.com/repository/docker/renigoms/payment-service/general)


## Configuração do Ambiente

* Precisei fazer algumas configurações para conseguir rodar o ambiente Kafka usando Docker Compose, e para facilitar a vida de quem quiser replicar o ambiente, criei um arquivo README específico para isso. Ele contém todas as instruções necessárias para configurar e rodar o ambiente Kafka com Zookeeper e Kafdrop usando Docker Compose. Isso foi necessário porque pelo que pesquisei o kafka atualmente não precisa mais do uso do zookeeper, mas o curso ainda utiliza ele, então para evitar problemas de compatibilidade, optei por seguir a configuração do curso. O arquivo README específico para a configuração do ambiente Kafka pode ser encontrado [aqui](./DOC_CONFIG_README.md).