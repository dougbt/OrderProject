# 🍽️ Real Time Order System

Sistema de pedidos em tempo real com arquitetura distribuída usando Spring Boot, Kafka, Redis e PostgreSQL.

## 🏗️ Arquitetura

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │───▶│  API Gateway│───▶│   Kafka     │
└─────────────┘    │ (Spring Boot)│    └─────────────┘
                   └─────────────┘           │
                          │                  │
                          ▼                  ▼
                   ┌─────────────┐    ┌─────────────┐
                   │ PostgreSQL  │    │    Redis    │
                   │   (Orders)  │    │   (Cache)   │
                   └─────────────┘    └─────────────┘
```

## 🚀 Tecnologias

- **Backend:** Spring Boot 3.5.3
- **Banco de Dados:** PostgreSQL 14
- **Cache:** Redis 7
- **Message Broker:** Apache Kafka 3
- **Java:** 21
- **Build Tool:** Maven

## 📋 Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven (opcional, projeto usa Maven Wrapper)

## 🛠️ Instalação e Execução

### 1. Clone o repositório
```bash
git clone <github.com/dougbt/OrderProject/tree/main>
cd OrderProject
```

### 2. Inicie os serviços com Docker Compose
```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL (porta 5432)
- Redis (porta 6379)
- Zookeeper (porta 2181)
- Kafka (porta 9092)

### 3. Execute a aplicação
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 API Endpoints

### Menu
```http
GET /menu
```
Retorna o menu disponível (com cache no Redis)

**Resposta:**
```json
{
  "availableItems": [
    {
      "id": 1,
      "name": "Hamburger",
      "price": 25.50,
      "category": "MEAL"
    }
  ]
}
```

### Pedidos
```http
POST /orders
Content-Type: application/json

{
  "customerId": "123",
  "status": "PENDING",
  "items": [
    {
      "name": "Hamburger",
      "price": 25.50,
      "category": "MEAL"
    },
    {
      "name": "Coca-Cola",
      "price": 8.50,
      "category": "DRINK"
    }
  ]
}
```

**Resposta:**
```json
{
  "id": 1,
  "customerId": "123",
  "status": "PENDING",
  "createdAt": "2025-07-09T15:30:00",
  "items": [...]
}
```

### Health Check
```http
GET /actuator/health
```

## 🔄 Fluxo de Processamento

1. **Cliente** envia pedido via API
2. **API Gateway** recebe e valida o pedido
3. **Kafka Producer** envia pedido para tópico `orders-topic`
4. **Kafka Consumer** processa o pedido
5. **PostgreSQL** salva o pedido (com fallback no Redis)
6. **Redis** cacheia o menu para consultas rápidas

## 🗄️ Estrutura do Projeto

```
src/main/java/br/com/OrderProject/OrderProject/
├── api/controller/           # Controllers REST
│   ├── OrderController.java
│   └── MenuController.java
├── domain/                   # Camada de domínio
│   ├── model/               # Entidades
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── Menu.java
│   ├── repository/          # Repositórios
│   │   ├── OrderRepository.java
│   │   └── OrderItemRepository.java
│   └── service/            # Serviços de negócio
│       ├── OrderService.java
│       ├── MenuService.java
│       └── FallbackService.java
├── infrastructure/          # Configurações e integrações
│   ├── config/
│   │   ├── KafkaConfig.java
│   │   ├── RedisConfig.java
│   │   └── SecurityConfig.java
│   └── kafka/
│       ├── KafkaProducerService.java
│       └── KafkaConsumerService.java
└── RealTimeOrderSystemApplication.java
```

## 🧪 Testes

Execute os testes:
```bash
./mvnw test
```

### Testes de Integração
- `KafkaTest.java` - Testa integração com Kafka
- `RealTimeOrderSystemApplicationTests.java` - Testa contexto da aplicação

## 🔧 Configurações

### application.yaml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/order_db
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
  redis:
    host: localhost
    port: 6379

kafka:
  bootstrap-servers: localhost:9092
  topic:
    orders: orders-topic

server:
  port: 8080
```

## 🐳 Docker

### Serviços Disponíveis
- **PostgreSQL:** `localhost:5432`
- **Redis:** `localhost:6379`
- **Kafka:** `localhost:9092`
- **Zookeeper:** `localhost:2181`

### Comandos Úteis
```bash
# Ver logs dos containers
docker-compose logs

# Parar todos os serviços
docker-compose down

# Reiniciar um serviço específico
docker-compose restart postgres
```

## 🚀 Desenvolvimento

### Hot Reload
O projeto usa Spring Boot DevTools para hot reload automático.

### Estrutura de Commits
- `feat:` - Novas funcionalidades
- `fix:` - Correções de bugs
- `chore:` - Tarefas de manutenção
- `docs:` - Documentação

## 📝 Próximos Passos

- [ ] Implementar autenticação JWT
- [ ] Adicionar validações de entrada
- [ ] Implementar logs estruturados
- [ ] Criar documentação Swagger/OpenAPI
- [ ] Adicionar testes unitários
- [ ] Implementar monitoramento com Prometheus
- [ ] Configurar CI/CD

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Douglas Barcellos**
- GitHub: [@dougbt](https://github.com/dougbt)

---

⭐ Se este projeto te ajudou, considere dar uma estrela!
