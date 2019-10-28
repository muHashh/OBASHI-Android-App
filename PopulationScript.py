import mysql.connector
from mysql.connector import Error
from mysql.connector import errorcode

connection = mysql.connector.connect(host='localhost',
                                     database='obashischema',
                                     user='root',
                                     password='Contraseña')

print("Connected!")
mySql_insert_query = """INSERT INTO devices (Name, Description, X_coord,
                    Y_coord, Z_coord) VALUES ('Lenovo ThinkPad P71',
                    'A laptop', '2', '1', '0') """

cursor = connection.cursor()
result = cursor.execute(mySql_insert_query)
connection.commit()
print("Record inserted successfully into devices table")
cursor.close()
