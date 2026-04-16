# 🛒 Santi-x Shop API

API REST para gerenciamento de **produtos, estoques e inventário**, com autenticação via **JWT** e controle de acesso por **roles (ADMIN, SELLER, CUSTOMER)**.

---

## 🚀 O que esse projeto resolve?

Sistema backend completo para e-commerce com foco em:

* 🔐 Segurança (JWT + autorização por perfil)
* 🧠 Regras de negócio reais (ownership de dados)
* ⚙️ Estrutura escalável (arquitetura em camadas)

---

## 🔐 Segurança

* Autenticação com **JWT**
* Proteção de rotas via filtro (`SecurityFilter`)
* Controle de acesso baseado em roles:

  * **ADMIN** → acesso total
  * **SELLER** → gerencia seus próprios dados
  * **CUSTOMER** → acesso restrito

---

## 📦 Funcionalidades

* 👤 Cadastro e login de usuários
* 🛍️ CRUD de produtos
* 📦 CRUD de estoques
* 📊 Controle de inventário (produto + estoque)
* ⚠️ Tratamento global de exceções

---

## 🧠 Regras de Negócio

* Um usuário só acessa **seus próprios dados**
* Inventory só pode ser criado com:

  * produto do usuário
  * estoque do usuário
* Validações completas (preço, quantidade, campos obrigatórios)

---

## ⚠️ Tratamento de Erros

Respostas padronizadas:

```json
{
  "code": "ERROR_CODE",
  "message": "Descrição do erro",
  "timestamp": "2026-04-01T14:30:00"
}
```

---

## 🏗️ Arquitetura

```
Controller → Service → Repository
```

* DTOs para entrada/saída
* Regras centralizadas no Service
* Segurança desacoplada com Spring Security

---

## 🚀 Diferenciais

✔ Autenticação stateless com JWT
✔ Controle de acesso por role + ownership
✔ Validação de segurança em múltiplas camadas
✔ Código preparado para escalar (pagamentos, pedidos, etc.)

---

## 🔜 Próximos passos

* Refresh Token
* Módulo de pagamentos
* Integração com frontend

---

## 🧱 Modelagem do Banco Protótipo das entidades: https://dbdiagram.io/d/69b37c06a9fdf1293d16c45c ![banco](https://github.com/user-attachments/assets/3a9118d8-3030-4066-ad6c-4171fd0959b1)
## 📌 Status

🚧 Em evolução — foco atual em segurança e regras de negócio.
