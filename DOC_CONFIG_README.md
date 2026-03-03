# Kafka Docker Compose - Configuração Completa

Este projeto configura um ambiente Kafka completo usando Docker Compose com Zookeeper e Kafdrop.

---

## 📋 Serviços

- **Zookeeper**: Gerenciador de cluster Kafka (porta 2181)
- **Kafka**: Message broker (porta 9092)
- **Kafdrop**: Interface web para monitoramento (porta 19000)

---

## 🚀 Como Usar

### Iniciar os serviços
```bash
docker compose up -d
```

### Ver status dos containers
```bash
docker compose ps
```

### Ver logs em tempo real
```bash
docker compose logs -f
```

### Ver logs de um serviço específico
```bash
docker compose logs -f kafka
docker compose logs -f kafdrop
docker compose logs -f zookeeper
```

### Parar os serviços
```bash
docker compose down
```

### Parar e remover volumes (limpar tudo)
```bash
docker compose down -v
```

### Reiniciar um serviço específico
```bash
docker compose restart kafka
```

---

## 🌐 Acessar Serviços

- **Kafdrop (Interface Web)**: `http://192.168.1.177:19000`
- **Kafka Broker**: `192.168.1.177:9092`
- **Zookeeper**: `192.168.1.177:2181`

---

## 📦 Comparação: Simples vs Production-Ready

### Configuração do Curso

```yaml
version: '3'

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    networks:
      - broker-kafka
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:latest
    networks:
      - broker-kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  kafdrop:
    image: obsidiandynamics/kafdrop:latest
    networks:
      - broker-kafka
    depends_on:
      - kafka
    ports:
      - "19000:9000"
    environment:
      KAFKA_BROKERCONNECT: kafka:29092

networks:
  broker-kafka:
    driver: bridge
```

### **1. ZOOKEEPER - Ports**

#### Simples
```yaml
ports: ❌ NENHUMA PORTA EXPOSTA
```

#### Production-Ready (Este projeto)
```yaml
ports:
  - "0.0.0.0:2181:2181"  ✅ PORTA EXPOSTA
```

**Motivo:** Docker não vincula automaticamente ao `localhost` da máquina. Precisa expor em `0.0.0.0` para acessar de qualquer interface de rede.

---

### **2. ZOOKEEPER - Imagem**

#### Simples
```yaml
image: confluentinc/cp-zookeeper:latest  ⚠️ Versão instável
```

#### Production-Ready (Este projeto)
```yaml
image: confluentinc/cp-zookeeper:7.4.0  ✅ Versão fixa
```

**Motivo:** `latest` pode mudar a qualquer momento e quebrar. Versão `7.4.0` é estável e testada.

---

### **3. KAFKA - Imagem**

#### Simples
```yaml
image: confluentinc/cp-kafka:latest  ❌ QUEBRA (exige KRaft)
```

#### Production-Ready (Este projeto)
```yaml
image: confluentinc/cp-kafka:7.4.0  ✅ FUNCIONA (usa Zookeeper)
```

**Motivo:** Versões `latest` exigem `KAFKA_PROCESS_ROLES` (modo KRaft). Versão `7.4.0` usa Zookeeper tradicional.

---

### **4. KAFKA - Ports**

#### Simples
```yaml
ports:
  - "9092:9092"  ❌ Só em localhost do container
```

#### Production-Ready (Este projeto)
```yaml
ports:
  - "0.0.0.0:9092:9092"  ✅ Em TODAS as interfaces
```

**Motivo:** `0.0.0.0` permite acesso de qualquer IP da máquina (192.168.1.177, localhost, etc).

---

### **5. KAFKA - ADVERTISED_LISTENERS**

#### Simples
```yaml
PLAINTEXT_HOST://localhost:9092  ❌ Não funciona no Docker
```

#### Production-Ready (Este projeto)
```yaml
PLAINTEXT_HOST://192.168.1.177:9092  ✅ IP REAL da máquina
```

**Motivo:** Clientes externos (aplicação Java, Kafdrop) precisam do IP real para se conectar. `localhost` no container aponta para o próprio container.

---

### **6. KAFKA - Healthcheck**

#### Simples
```yaml
❌ NENHUM HEALTHCHECK
```

#### Production-Ready (Este projeto)
```yaml
healthcheck:
  test: ["CMD", "kafka-broker-api-versions", "--bootstrap-server", "kafka:29092"]
  interval: 10s
  timeout: 10s
  retries: 5
  start_period: 40s  ✅ COMPLETO
```

**Motivo:** Verifica se Kafka está realmente funcionando antes do Kafdrop iniciar. Evita erros de conexão.

---

### **7. KAFDROP - Imagem**

#### Simples
```yaml
image: obsidiandynamics/kafdrop:latest  ⚠️ Versão instável
```

#### Production-Ready (Este projeto)
```yaml
image: obsidiandynamics/kafdrop:3.3.0  ✅ Versão estável
```

**Motivo:** Versão específica garante compatibilidade e evita surpresas em atualizações.

---

### **8. KAFDROP - Ports**

#### Simples
```yaml
ports:
  - "19000:9000"  ❌ Só em localhost do container
```

#### Production-Ready (Este projeto)
```yaml
ports:
  - "0.0.0.0:19000:9000"  ✅ Em TODAS as interfaces
```

**Motivo:** Permite acessar `http://192.168.1.177:19000` de qualquer lugar na rede.

---

### **9. KAFDROP - Variáveis de Ambiente**

#### Simples
```yaml
environment:
  KAFKA_BROKERCONNECT: kafka:29092  ❌ INCOMPLETO
```

#### Production-Ready (Este projeto)
```yaml
environment:
  KAFKA_BROKERCONNECT: kafka:29092
  ZK_HOSTS: zookeeper:2181           ✅ COMPLETO
  ZOOKEEPER_CONNECT: zookeeper:2181
  SERVER_PORT: 9000
```

**Motivo:** Kafdrop precisa se conectar ao Zookeeper para listar tópicos e configurações.

---

### **10. KAFDROP - Restart Policy**

#### Simples
```yaml
❌ NENHUMA POLÍTICA DE RESTART
```

#### Production-Ready (Este projeto)
```yaml
restart: on-failure  ✅ REINICIA AUTOMATICAMENTE
```

**Motivo:** Se Kafdrop falhar (ex: Kafka não estava pronto), ele tenta novamente automaticamente.

---

### **11. KAFDROP - Healthcheck**

#### Simples
```yaml
❌ NENHUM HEALTHCHECK
```

#### Production-Ready (Este projeto)
```yaml
healthcheck:
  test: ["CMD", "curl", "-f", "http://localhost:9000"]
  interval: 30s
  timeout: 10s
  retries: 3
  start_period: 60s  ✅ COMPLETO
```

**Motivo:** Verifica se a interface web está respondendo.

---

### **12. KAFDROP - Depends On**

#### Simples
```yaml
depends_on:
  - kafka  ❌ SIMPLES (não espera estar pronto)
```

#### Production-Ready (Este projeto)
```yaml
depends_on:
  kafka:
    condition: service_healthy  ✅ ESPERA KAFKA SAUDÁVEL
```

**Motivo:** Kafdrop só inicia quando Kafka passa no healthcheck. Evita erro "No resolvable bootstrap urls".

---

## 📊 Resumo das Diferenças

| # | Item | Simples | Este Projeto | Impacto |
|---|------|---------|-------------|---------|
| 1 | Versões | latest ⚠️ | 7.4.0 ✅ | Estabilidade |
| 2 | Ports | Mínimo ❌ | 0.0.0.0 ✅ | Acessibilidade |
| 3 | IP | localhost ❌ | 192.168.1.177 ✅ | Conectividade |
| 4 | Healthchecks | Nenhum ❌ | 2 ✅ | Confiabilidade |
| 5 | Restart | Não ❌ | Sim ✅ | Resiliência |
| 6 | Variáveis ZK | Não ❌ | Sim ✅ | Funcionalidade |
| 7 | Condition | Não ❌ | Sim ✅ | Ordem de inicialização |

---