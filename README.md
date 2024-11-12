# Projeto Java Spring Boot

Este projeto foi desenvolvido utilizando:
- Java 17
- Spring Boot
- Bancos de dados:
  - H2 (Testes)
  - MariaDB v10.11 (Desenvolvimento)

## 🚀 Configuração do Ambiente

### Pré-requisitos
- Java 17 instalado
- Maven instalado
- MariaDB v10.11 (para ambiente de desenvolvimento)

### Banco de Dados

#### MariaDB
1. Criar o banco de dados:
```sql
CREATE DATABASE db_assembleia;
```

## 💻 Executando o Projeto

### Ambiente de Testes (H2)
Para executar o projeto utilizando o banco H2:
1. Configure no arquivo `application.properties`:
```properties
spring.profiles.active=${APP_PROFILE:test}
```
2. Execute o projeto

### Ambiente de Desenvolvimento (MariaDB)
Para executar o projeto utilizando o MariaDB:
1. Configure no arquivo `application.properties`:
```properties
spring.profiles.active=${APP_PROFILE:dev}
```
2. Certifique-se que o banco `db_assembleia` foi criado
3. Execute o projeto

## 📚 Documentação API

A documentação da API está disponível através do Swagger UI:
- URL: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 🔍 Perfis Disponíveis

O projeto possui dois perfis de configuração:

1. **test**: Utiliza banco H2 em memória
   - Ideal para testes e desenvolvimento local rápido
   - Banco de dados é recriado a cada execução

2. **dev**: Utiliza MariaDB
   - Ambiente de desenvolvimento
   - Requer banco MariaDB configurado

## 📝 Notas Importantes

- Certifique-se de ter todas as dependências instaladas antes de executar o projeto
- Verifique se as portas necessárias (8080 para a aplicação, 3306 para MariaDB) estão disponíveis
- Para alternar entre os perfis, modifique a configuração `spring.profiles.active` conforme necessário

