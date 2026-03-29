# 🛒 Santi-x Shop - API REST

API REST para gerenciamento de usuários, produtos, estoques e inventário, com controle de permissões por tipo de usuário.

---

## 👤 Primeira REST [USER]

### 🧪 Testes no Postman

### 📥 Requisições [USER]

![Requisições](https://github.com/user-attachments/assets/a71f2d34-4611-4dbe-b98c-3a8444776b2f)

---

### 📤 Banco [USER]

![Respostas](https://github.com/user-attachments/assets/ff037d4c-f110-440e-ad03-caaeed929246)

---

## 📦 Construção da REST [STOCK]

### 🧪 Testes no Postman

### 📥 Requisições [STOCK]

<img width="1135" height="411" alt="image" src="https://github.com/user-attachments/assets/c0bffb5d-fcbd-4425-a9e0-386b87c6fb97" />

---

### 📤 Banco [STOCK]

![Banco STOCK](https://github.com/user-attachments/assets/b3de87ad-0319-41af-919d-1a0f6197d83d)

---

### ⚙️ Regras de Negócio (em construção)

* Apenas usuários do tipo **SELLER** podem criar estoques
* Cada estoque pertence a um único usuário
* Validação de ownership:

  * Um usuário só pode visualizar, editar ou deletar seus próprios estoques

---

## 📦 Construção da REST [PRODUCT]

### 🧪 Testes no Postman

### 📥 Requisições [PRODUCT]

<img width="1181" height="342" alt="image" src="https://github.com/user-attachments/assets/b796a03d-c810-45b1-b4a4-38f4cfb08302" />

---

### 📤 Banco [PRODUCT]

<img width="970" height="87" alt="image" src="https://github.com/user-attachments/assets/0cb6b37d-d137-41fc-82af-348c1e3adbaf" />

---

### ⚙️ Regras de Negócio (em construção)

* Apenas usuários do tipo **SELLER** podem criar produtos
* Cada produto pertence a um usuário
* Validação de ownership:

  * Um usuário só pode manipular seus próprios produtos
* Validações:

  * Nome, descrição e SKU obrigatórios
  * Preço deve ser maior que zero

---

## 📦 Construção da REST [INVENTORY]

### 🧪 Testes no Postman

### 📥 Requisições [INVENTORY]

<img width="1147" height="522" alt="image" src="https://github.com/user-attachments/assets/9c0fcf36-fb90-4456-9aac-4e38c9bfe0d8" />

---

### 📤 Banco [INVENTORY]

<img width="783" height="65" alt="image" src="https://github.com/user-attachments/assets/58437b28-0033-496c-8355-d1c35c4e238b" />

---

### ⚙️ Regras de Negócio (em construção)

* O inventory depende de:

  * um **produto**
  * um **estoque**
* Ambos devem pertencer ao mesmo usuário (**SELLER**)
* Validação de ownership:

  * O usuário só pode manipular inventories vinculados aos seus próprios produtos e estoques
* Quantidade não pode ser negativa

---

### 🔄 Integração entre módulos

* Inventory conecta:

  * Product → Stock
* Representa a quantidade de produtos disponíveis em um determinado estoque
* Base para futuras funcionalidades:

  * Controle de vendas
  * Baixa automática de estoque

---

## 🧱 Modelagem do Banco

Protótipo das entidades:
https://dbdiagram.io/d/69b37c06a9fdf1293d16c45c

![banco](https://github.com/user-attachments/assets/3a9118d8-3030-4066-ad6c-4171fd0959b1)

---

## 🚀 Evolução do Projeto

* ✔️ CRUD de usuários
* ✔️ Controle de permissões (SELLER / CUSTOMER)
* ✔️ REST de estoque com vínculo ao usuário
* ✔️ REST de produtos com validação de ownership
* ✔️ REST de inventory conectando produto e estoque
* 🔄 Padronização de autenticação via header (`userId`) *(temporário)*
* 🚧 Futuro: implementação de autenticação real com JWT

---

## 🧠 Observações

* Arquitetura baseada em camadas (Controller → Service → Repository)
* Validações centralizadas na camada de **Service**
* Uso de DTOs para entrada e saída de dados
* Estrutura preparada para evolução com autenticação e regras mais robustas

---

## 📌 Status do Projeto

🚧 Em desenvolvimento — foco atual em regras de negócio, validações e estrutura de domínio.
