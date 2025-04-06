# API FrameSnap Video

API para processamento e gerenciamento de vídeos com extração de frames.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Endpoints da API](#endpoints-da-api)
- [Boas Práticas](#boas-práticas)
- [Testes](#testes)
- [Monitoramento e Logs](#monitoramento-e-logs)
- [Segurança](#segurança)
- [Contribuição](#contribuição)

## 🎯 Visão Geral

O FrameSnap Video é uma API que permite o upload, processamento e gerenciamento de vídeos. A API oferece funcionalidades como:
- Upload de vídeos
- Extração de frames (thumbnails)
- Download de vídeos processados
- Gerenciamento de status de processamento
- Consulta de vídeos por usuário

## 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- AWS Services:
  - S3 para armazenamento
  - DynamoDB para persistência
  - SQS para filas
  - Redis para cache
- Docker e Docker Compose
- Gradle
- JUnit 5
- Mockito

## 🏗 Arquitetura

O projeto segue os princípios da Arquitetura Hexagonal (Ports and Adapters) e Clean Architecture:

```
src/
├── main/
│   ├── java/
│   │   └── com/fiap/framesnap/
│   │       ├── application/        # Casos de uso e regras de negócio
│   │       ├── crosscutting/       # Configurações e utilitários
│   │       ├── entities/           # Entidades do domínio
│   │       └── infrastructure/     # Adaptadores e implementações
│   └── resources/
│       └── application.yml         # Configurações da aplicação
└── test/                          # Testes automatizados
```

## ⚙️ Configuração do Ambiente

### Pré-requisitos

- Java 17
- Docker e Docker Compose
- AWS CLI configurado (para desenvolvimento local)

### Configuração Local

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/framesnap-video.git
cd framesnap-video
```

2. Configure as variáveis de ambiente:
```bash
cp .env.example .env
# Edite o arquivo .env com suas configurações
```

3. Inicie os serviços locais:
```bash
docker-compose up -d
```

4. Execute a aplicação:
```bash
./gradlew bootRun
```

## 🌐 Endpoints da API

### Upload de Vídeo

```http
POST /videos/init-upload
Content-Type: application/json

{
    "fileName": "video.mp4",
    "userEmail": "usuario@email.com"
}
```

**Resposta:**
```json
{
    "videoId": "uuid-do-video",
    "presignedUrl": "url-para-upload"
}
```

### Download de Vídeo

```http
GET /videos/download?videoId=uuid-do-video
```

**Resposta:**
```json
{
    "downloadUrl": "url-para-download"
}
```

### Status do Vídeo

```http
GET /videos/{videoId}/status
```

**Resposta:**
```json
{
    "status": "COMPLETED",
    "thumbnailFileName": "thumb.jpg",
    "thumbnailUrl": "url-do-thumbnail",
    "processedAt": "2024-04-06T14:30:00Z"
}
```

### Vídeos do Usuário

```http
GET /videos/user/{userEmail}
```

**Resposta:**
```json
{
    "videos": [
        {
            "videoId": "uuid-do-video",
            "fileName": "video.mp4",
            "status": "COMPLETED",
            "thumbnailFileName": "thumb.jpg",
            "thumbnailUrl": "url-do-thumbnail",
            "downloadUrl": "url-para-download",
            "processedAt": "2024-04-06T14:30:00Z"
        }
    ]
}
```

### Download de Thumbnails

```http
GET /videos/{videoId}/thumbnails
```

**Resposta:**
```json
{
    "fileName": "thumb.jpg",
    "contentType": "image/jpeg",
    "base64Content": "base64-do-conteudo"
}
```

## ✨ Boas Práticas

### Código

1. **Clean Code**
   - Nomes descritivos para classes, métodos e variáveis
   - Métodos pequenos e com responsabilidade única
   - Comentários apenas quando necessário
   - Código autoexplicativo

2. **SOLID**
   - Single Responsibility Principle
   - Open/Closed Principle
   - Liskov Substitution Principle
   - Interface Segregation Principle
   - Dependency Inversion Principle

3. **Padrões de Projeto**
   - Repository Pattern
   - Factory Pattern
   - Strategy Pattern
   - Adapter Pattern

### Arquitetura

1. **Hexagonal Architecture**
   - Separação clara entre domínio e infraestrutura
   - Inversão de dependência
   - Ports and Adapters

2. **DDD (Domain-Driven Design)**
   - Entidades ricas
   - Value Objects
   - Aggregates
   - Domain Events

### Testes

1. **Testes Unitários**
   - Cobertura mínima de 80%
   - Testes isolados
   - Mocks quando necessário
   - Nomenclatura clara

2. **Testes de Integração**
   - Testes de fluxos completos
   - Testes de adaptadores
   - Testes de casos de erro

### Segurança

1. **Autenticação e Autorização**
   - JWT para autenticação
   - RBAC para autorização
   - Validação de tokens

2. **Proteção de Dados**
   - Criptografia em trânsito (HTTPS)
   - Criptografia em repouso
   - Sanitização de inputs

### Performance

1. **Otimizações**
   - Cache quando apropriado
   - Paginação em listagens
   - Compressão de respostas
   - Lazy loading

2. **Monitoramento**
   - Métricas de performance
   - Logs estruturados
   - Rastreamento de erros

## 🧪 Testes

### Executando Testes

```bash
# Todos os testes
./gradlew test

# Testes específicos
./gradlew test --tests "com.fiap.framesnap.application.video.usecases.*"

# Relatório de cobertura
./gradlew jacocoTestReport
```

### Cobertura de Testes

- Testes Unitários: 80%+
- Testes de Integração: Fluxos críticos
- Testes de Performance: Benchmarks

### Troubleshooting

#### Erros Comuns

1. **Erro de AWS Credentials**
   ```
   software.amazon.awssdk.core.exception.SdkClientException
   ```
   **Solução**: Configure as credenciais AWS para testes:
   ```bash
   export AWS_ACCESS_KEY_ID=test
   export AWS_SECRET_ACCESS_KEY=test
   export AWS_REGION=us-east-1
   ```

2. **Erro de Spring Context**
   ```
   org.springframework.beans.factory.UnsatisfiedDependencyException
   ```
   **Solução**: Verifique se todas as dependências necessárias estão configuradas no `application-test.yml`

3. **Avisos de Depreciação**
   ```
   The 'sonarqube' task depends on compile tasks...
   ```
   **Solução**: Adicione ao `gradle.properties`:
   ```properties
   sonar.gradle.skipCompile=true
   ```

4. **Erros de Compilação**
   ```
   Note: Some input files use unchecked or unsafe operations
   ```
   **Solução**: Adicione ao `build.gradle`:
   ```groovy
   tasks.withType(JavaCompile) {
       options.compilerArgs << "-Xlint:unchecked"
   }
   ```

5. **Erro de Propriedade Gradle**
   ```
   Value 'warning ' given for org.gradle.warning.mode Gradle property is invalid
   ```
   **Solução**: Use um dos valores válidos no `gradle.properties`:
   ```properties
   org.gradle.warning.mode=none
   ```
   Os valores válidos são: `none`, `warning`, `error`, `all`

6. **Erro de Formato YAML no GitHub Actions**
   ```
   Incorrect type. Expected "string".
   ```
   **Solução**: Use o formato correto para variáveis de ambiente:
   ```yaml
   env:
     VARIABLE_NAME: "valor"
   ```
   em vez de:
   ```yaml
   env:
     - VARIABLE_NAME=valor
   ```

## 📊 Monitoramento e Logs

### Logs

- Logs estruturados em JSON
- Níveis: ERROR, WARN, INFO, DEBUG
- Rastreamento por request ID

### Métricas

- Tempo de resposta
- Taxa de erro
- Uso de recursos
- Status dos serviços

## 🔒 Segurança

### Autenticação

- JWT com expiração
- Refresh tokens
- Blacklist de tokens

### Autorização

- RBAC (Role-Based Access Control)
- Permissões granulares
- Validação de recursos

### Dados

- Criptografia em trânsito (HTTPS)
- Criptografia em repouso
- Sanitização de inputs
- Validação de dados

## 👥 Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

### Padrões de Commit

- feat: Nova funcionalidade
- fix: Correção de bug
- docs: Documentação
- style: Formatação
- refactor: Refatoração
- test: Testes
- chore: Manutenção

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.








