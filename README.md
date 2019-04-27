# Revolut
It has two resources - Accounts(To create bak accounts) and Transactions(To transfer money between two bank accounts).

# Requires
 Java 8
 Maven
# How to start
Once the application is fetched from git it can be built with maven

mvn clean install
This will fetch dependencies and run all tests

To run the app execute:

mvn exec:java

# API Definition - 
Please check swagger yaml documentation and Revolut-Account-Transfer.postman_collection.json file to check API documentation.


swagger: "2.0"
info:
 description: "This is a documentation for Money Transfer application"
 version: "1.0.0"
 title: "MoneyTransfer"
 
 contact:
   email: "avikkesari16@gmail.com"
 
host: "localhost:8080"
basePath: ""
tags:
- name: "Account"
  description: "Create,Get and Update,Delete Account"
- name: "Transaction"
  description: "Get and Create Transaction"

paths:
  /accounts:
    
    x-summary: 'bank Accounts Configuration'
    get:
      tags:
      - "Account"
      
      summary: 'List All Bank Accounts'
      responses:
        200:
          description: 'Returns a list of Bank Accounts'
          schema:
            type: 'array'
            items:
              $ref: '#/definitions/AccountResponse'
          
    post:
      tags:
      - "Account"
      
      summary: Create a New Bank Account
      consumes:
      - application/json
      produces:
      - application/json
      parameters:
      - in: body
        name: body
        required: true
        schema:
          type: "array"
          items:
            $ref: "#/definitions/Account"
      responses:
        200:
          description: 'Successful creation of a Bank Account '
          schema:
            $ref: '#/definitions/AccountResponse'        
        
        
  /accounts/{id} :
    get:
      tags:
      - "Account"
      
      summary: 'Details Of Bank Account with specified id'
      parameters:  
          - name: "id"
            in: "path"
            description: "id of bank Account which is to be retrieved"
            required: true
            type: "number"
            format: "Long"
      responses:
        200:
          description: 'Returns the details of Bank Account'
          schema:
            type: 'object'
            items:
              $ref: '#/definitions/AccountResponse'
    delete:
        tags:
        - "Account"
        
        summary: 'Deletes the specified Account from DataBase if exists'
        parameters:  
          - name: "id"
            in: "path"
            description: "id of bank Account which is to be deleted"
            required: true
            type: "number"
            format: "Long"
        responses:
          200:
             description: 'Successfull deletion of bank Account'
  
    put:
        tags:
        - "Account"
        
        summary: 'Update the nick name of account with specified id'
        parameters:  
          - name: "id"
            in: "path"
            description: "Id of bankAccount which is to be updated"
            required: true
            type: "integer"
            format: "int64"
          - in: body
            name: body
            required: true
            schema:
              $ref: "#/definitions/Name"
                 
          
            
        responses:
          200:
            description: 'Successfull updation of Bank Account'
            schema:
                $ref: '#/definitions/AccountResponse'
    
  /transactions:
    x-summary: 'Transaction creation and retrieval'
    get:
      tags:
        - "Transaction"
      description: >-
        Returns all the transaction
      summary: 'List All Transcations'
      
      responses:
        200:
          description: 'Returns a list of all transactions'
          schema:
            type: "array"
            items:
              $ref: "#/definitions/TransactionResponse"
    
    post:
      tags:
      - "Transaction"
      description: >-
        To create new Bank Transaction between two account holders with status processing,on successful completion the status is changed to success
      summary: Create a New Transaction
      consumes:
      - application/json
      produces:
      - application/json
      parameters:
      - in: body
        name: body
        required: true
        schema:
          type: "array"
          items:
            $ref: "#/definitions/Transaction"
      responses:
        200:
          description: 'Successful creation of a Transaction'
          schema:
            $ref: '#/definitions/TransactionResponse'        
  /transcations/{id}:
   
    get:
      tags:
        - "Transaction"
      description: >-
        Returns  the transaction by Id
      summary:  details of transaction with specified Id'
      parameters:  
        - name: "id"
          in: "path"
          description: "Transaction Id which is to be retrieved"
          required: true
          type: "integer"
          format: "int64"
      responses:
        200:
          description: 'Returns the detail of transaction with specified'
          schema:
            type: "object"
            items:
              $ref: "#/definitions/TransactionResponse"
             
    
definitions:
  
  Account:
   type: "object"
   required:
   - "ownerName"
   - "balance"
   properties:
     id:
       type: "integer"
       format: "int64"
     
     ownerName:
       type: "string"
       example: "avik"
     nickName:
       type: "string"
       example: "kesari"
     balance:
       type: "number"
       example: 2000.0
     currency:
       type: "string"
       example: "USD"
  Transaction:
   type: "object"
   required:
   - "amounttoBeTransfered"
   - "fromBankAccountId"
   - "toBankAccountId"
   properties:
     fromBankAccountId:
       type: "number"
       format: "long"
       example: "1"
     toBankAccountId:
       type: "number"
       format: "long"
       example: "2"
     
     amountToBeTransfered:
       type: "number"
       example: 2000.0
     currency:
       type: "string"
       example: "USD"
  TransactionResponse:
   type: "object"
   
   properties:
     id:
       type: "integer"
       format: "int64"
       example: "1"
     fromBankAccountId:
       type: "number"
       format: "long"
       example: "1"
     toBankAccountId:
       type: "number"
       format: "long"
       example: "2"
     
     amountToBeTransfered:
       type: "number"
       example: 2000.0
     currency:
       type: "string"
       example: "USD"
     creationDate:
       type: "string"
       format: "date-time"
       example: "1556366927457"
     updationDate:
       type: "string"
       format: "date-time"
       example: "1556366927457"
     status:
       type: "string"
       enum:
       - "PROCESSING"
       - "FAILED"
       - "SUCCESS"
       
     message:
       type: "string"
       example: "USD"
  Name:
    type: "object"
    properties:
      name:
       type: "string"
       example: "avi"
  AccountResponse:
   type: "object"
   required:
   - "ownerName"
   - "balance"
   properties:
     id:
       type: "integer"
       format: "int64"
     
     ownerName:
       type: "string"
       example: "avik"
     nickName:
       type: "string"
       example: "kesari"
     balance:
       type: "number"
       example: 2000.0
     blockedAmount:
       type: "number"
       example: 1000.0  
       
  

