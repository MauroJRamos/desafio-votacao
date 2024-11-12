# Projeto Assembleia

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
![image](https://github.com/user-attachments/assets/05d4e15d-de1b-49b4-983f-817f4873d379)


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

2. No arquivo `application-dev.properties`, configure:
```properties
# Configurações do Banco de Dados
spring.datasource.url=jdbc:mariadb://localhost:3306/db_assembleia
spring.datasource.username=root
spring.datasource.password=123456

# Configuração do Hibernate
spring.jpa.hibernate.ddl-auto=update
```

3. Certifique-se que o banco `db_assembleia` foi criado
4. Execute o projeto

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
   - Gera automaticamente as tabelas através do Hibernate
   - Configurações padrão:
     - URL: jdbc:mariadb://localhost:3306/db_assembleia
     - Usuário: root
     - Senha: 123456

## 📝 Notas Importantes

- Certifique-se de ter todas as dependências instaladas antes de executar o projeto
- Verifique se as portas necessárias (8080 para a aplicação, 3306 para MariaDB) estão disponíveis
- Para alternar entre os perfis, modifique a configuração `spring.profiles.active` conforme necessário
- A configuração `spring.jpa.hibernate.ddl-auto=update` no perfil dev permite que o Hibernate atualize automaticamente o esquema do banco de dados conforme as entidades da aplicação
- Caso necessário, ajuste as credenciais do banco de dados no arquivo `application-dev.properties` de acordo com sua configuração local
