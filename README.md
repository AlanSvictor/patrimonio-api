# Controle de Patrimôni api

Essa ferramenta visa controlar o cadastro de patrimônios

Para popular os dados devem ser utilizados os seguintes endponts:
http://localhost:8080/api/marcas
http://localhost:8080/api/patrimônios
http://localhost:8080/api/usuarios

Os testes não utiliza, arquivos de xml ou .sql para popular os dados, facilitando a reutilização de código/dados e melhora a cobertura de testes, uma vez que para montar os ambientes com os dados utilizam os próprios serviços e repositórios, para que os dados sejam criados exatamente como são criados no sistema, utilizando as mesmas regras.

Neste projeto é utilizado o banco de dados H2 (Armazenamento em memória) para testes e PostgreSQL para ambiente dev.

Foi adicionado arquivo Docker-compose para facilizar o uso se necessário.
