
# SISRH - Sistema de Recursos Humanos

Projeto acadêmico desenvolvido com o objetivo de praticar o uso de APIs REST e SOAP em Java, utilizando a estrutura de um sistema fictício de Recursos Humanos.

## 📚 Tecnologias Utilizadas

- Java 11+
- Maven
- Jakarta EE / Servlets
- JAX-RS (API REST)
- JAX-WS (API SOAP)
- Eclipse IDE

## 🔧 Estrutura do Projeto

```
sisrh/
├── src/
│   └── main/
│       └── java/
│           └── sisrh/
│               ├── banco/          # Simulação de banco de dados em memória
│               ├── dto/            # Objetos de Transferência de Dados
│               ├── exception/      # Classe de exceção customizada
│               ├── rest/           # Implementações REST
│               ├── soap/           # Implementações SOAP
│               ├── seguranca/      # Autenticação básica
│               └── servlet/        # Inicialização da base de dados
├── pom.xml                         # Gerenciador de dependências Maven
```

## 🚀 Como executar

1. Importe o projeto no Eclipse como um projeto Maven.
2. Certifique-se de estar usando um servidor compatível com Jakarta EE (Tomcat, GlassFish, etc.).
3. Execute o servlet `InicializarBancoServlet` para carregar dados fictícios.
4. Acesse os endpoints REST e WSDL conforme os exemplos abaixo.

## 📡 Exemplos de Endpoints

### REST
```
GET /api/empregados
GET /api/solicitacoes
```

### SOAP
- WSDL: `/ws/ServicoEmpregado?wsdl`
- Métodos disponíveis: `listarEmpregados()`, `buscarEmpregado(id)`, etc.

## 👨‍💻 Autor

Projeto acadêmico desenvolvido por Nathan Oliveira.

---

Este projeto tem fins didáticos e visa exercitar boas práticas com serviços REST e SOAP em Java.
