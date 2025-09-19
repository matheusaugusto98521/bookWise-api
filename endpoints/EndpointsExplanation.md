# 📖 Endpoints da API

## 🔑 Autenticação

- URL: http://localhost:8080/security/login
- Body: 
    ```
    {
        "username" : String,
        "password" : String
    }
    ```
- Resultado(Se os dados estiverem corretos): 
    ```
    {
	  "message": "Usuário logado com sucesso",
	  "token": token em formato String
    }
    ```

## 🧑 Usuário

#### Registrar Usuário
- URL: http://localhost:8080/access/create
- Body: 
    ```
    {
	   "name" : String,
	   "username" : String,
	   "emailUser" : String,
	   "password" : String,
	   "role" : "Admin" ou "User"
    }
    ```
- Resultado:
    ```
    {
	   "message": "Usuário criado com sucesso!",
	   "data": {
		   "idUser": UUID(String)
		   "name": String,
		   "username": String,
		   "emailUser": String,
		   "password": Senha já codificada com hash,
		   "role": a role escolhida em formato de enum(type string),
		   "createdAt": LocalDateTime,
		   "enabled": true,
		   "authorities": [
			   aqui as authorities de acordo com sua role
		   ],
		   "credentialsNonExpired": true,
		   "accountNonExpired": true,
		   "accountNonLocked": true
	   }
    }
    ```
#### Alterar Informaçãos de Usuário
- URL: http://localhost:8080/access/update?idUser=<id do usuario>
- Auth: Bearer Token <Token obtido no login>
- Body:
    ```
    {
	   "name" : String,
	   "username" : String,
	   "emailUser" : String,
	   "password" : String,
	   "role" : "Admin" ou "User"
    }
    ```
- Resultado:
    ```
    {
	   "message": "Usuário Atualizado com sucesso!",
	   "data": {
		   "idUser": UUID(String)
		   "name": String,
		   "username": String,
		   "emailUser": String,
		   "password": Senha já codificada com hash,
		   "role": a role escolhida em formato de enum(type string),
		   "createdAt": LocalDateTime,
		   "enabled": true,
		   "authorities": [
			   aqui as authorities de acordo com sua role
		   ],
		   "credentialsNonExpired": true,
		   "accountNonExpired": true,
		   "accountNonLocked": true
	   }
    }
    ```

### Deletar Usuário
- URL: http://localhost:8080/access/delete?idUser=<id do usuario>
- Auth: Bearer Token <Token obtido no login>
- No Body
- Resultado:
    ```
       Usuário deletado com sucesso!
    ```

### Procurar Usuário pelo id
- URL: http://localhost:8080/access/get-by-id?idUser=<Id do usuario>
- Auth: Bearer Token <Token obtido no login>
- No Body
- Resultado:
    ```
    {
	   "message": "Usuário encontrado para o ID: id fornecido",
	   "data": {
		   "idUser": UUID(String)
		   "name": String,
		   "username": String,
		   "emailUser": String,
		   "password": Senha já codificada com hash,
		   "role": a role escolhida em formato de enum(type string),
		   "createdAt": LocalDateTime,
		   "enabled": true,
		   "authorities": [
			   aqui as authorities de acordo com sua role
		   ],
		   "credentialsNonExpired": true,
		   "accountNonExpired": true,
		   "accountNonLocked": true
	   }
    }
    ```

### Mostrar todos usuários existentes no banco
- URL: http://localhost:8080/access/
- Auth: Bearer Token <Token obtido no login>
- No Body
- Resultado:
    ```
        "message": "Usuários: ",
        "data" : [lista dos usuários]
    ```

### Mostrar livros que estão ocupados por usuários
- URL: http://localhost:8080/access/books?idUser=<Id do usuario>
- Auth: Bearer Token <Token obtido no login>
- No Body
- Resultado: 
    ```
    {
	    "message": "Livros emprestados: ",
	    "data": [lista dos livros relacionados aos usuários que pegaram emprestado]
    }
    ```


## ✔️ Categoria

### Cadastrar Nova Categoria
- URL: http://localhost:8080/category/create
- Auth: Bearer Token <Token obtido no login>
- Body:
    ```
    {
	    "nameCategory" : String,
	    "descriptionCategory" : String
    }
    ```
- Resultado:
    ```
    {
	    "message": "Categoria salva com sucesso:",
	    "data": {
		    "idCategory": UUID(String),
		    "nameCategory": String,
		    "descriptionCategory": String,
		    "createdAt": LocalDateTime
	    }
    }
    ```

### Alterar dados da Categoria
- URL: http://localhost:8080/category/update?idCategory=<Id Categoria>
- Auth: Bearer Token <Token obtido no login>
- Body:
    ```
    {
	    "nameCategory" : String,
	    "descriptionCategory" : String
    }
    ```
- Resultado:
    ```
    {
	    "message": "Categoria alterada com sucesso:",
	    "data": {
		    "idCategory": UUID(String),
		    "nameCategory": String,
		    "descriptionCategory": String,
		    "createdAt": LocalDateTime
	    }
    }
    ```

### Deletar Categoria
- URL: http://localhost:8080/category/delete?idCategory=<Id Categoria>
- Auth: Bearer Token <Token obtido no login>
- No Body
- Resultado:
    ```
        Categoria deletada com sucesso!
    ```

### Procurar Categoria Pelo ID
- URL: http://localhost:8080/category/get-by-id?idCategory=<Id Categoria>
- Auth: Bearer Token <Token obtido no login>
- No Body
- Resultado:
    ```
    {
		"message": "Categoria encontrada: ",
		"data": {
			"idCategory": UUID(String),
			"nameCategory": String,
			"descriptionCategory": String,
			"createdAt": LocalDateTime
		}
	}
    ```

### Mostrar Todas as Categorias
- URL: http://localhost:8080/category/
- No Body
- Resultado:
	```
	{
		"message": "Categorias: ",
		"data": [Lista de categorias]
	}
	```
