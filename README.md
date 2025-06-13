# Payment-Destaxa

## 🚀 Sobre o Projeto
Payment-Destaxa é um sistema de pagamentos desenvolvido em Java com Spring Boot, criado para demonstrar minhas habilidades técnicas em desenvolvimento back-end. Este projeto inclui funcionalidades como processamento de transações, gestão de usuários e integração com APIs de pagamento.

## 🔗 Link do Repositório: https://github.com/ygormacedo/Payment-Destaxa

## ✨ Funcionalidades Principais
✅ Processamento de pagamentos com múltiplos métodos (cartão, PIX, boleto)

✅ Gestão de usuários e perfis de acesso

✅ Integração com APIs de terceiros (ex: Stripe, PayPal)

✅ Validação de transações e segurança

✅ Documentação Swagger para fácil teste das APIs

## 🛠️ Tecnologias Utilizadas
Linguagem: Java 11+

Framework: Spring Boot 2.7+

Banco de Dados: PostgreSQL / H2 (para testes)

Ferramentas:

Maven

Docker (para containerização)

Swagger (documentação de APIs)

JUnit/Mockito (testes unitários)

##📦 Como Executar o Projeto
Pré-requisitos
Java JDK 11+

Maven

Docker (opcional)

Instalação
bash
# Clone o repositório
git clone https://github.com/ygormacedo/Payment-Destaxa.git

# Entre na pasta do projeto
cd Payment-Destaxa

# Instale as dependências
mvn install

# Execute a aplicação
mvn spring-boot:run
Com Docker
bash
docker-compose up --build
📚 Documentação da API
Acesse a documentação Swagger após iniciar a aplicação:

Testando a API com Postman
1. Requisição de Autorização
Método: POST
URL: http://localhost:8080/api/payments/authorization
Headers:
 KEY      -      VALUE
x-identifier: MERCHANT123

BODY (JSON)
{
    "external_id": "ORDER12345",
    "value": 23.80,
    "card_number": "4111111111111111",
    "installments": 2,
    "cvv": "123",
    "exp_month": 11,
    "exp_year": 28,
    "holder_name": "Destaxa"
}

🧪 Testes
Para executar os testes:

bash
mvn test
🤝 Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

📧 Contato
Ygor Macedo
[ygormacedo44@gmail.com]
LinkedIn

⭐️ Se gostou do projeto, deixe uma estrela no repositório!
