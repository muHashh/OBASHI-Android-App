import mysql.connector
from mysql.connector import Error
from mysql.connector import errorcode

connection = mysql.connector.connect(host='localhost',
                                     database='obashischema',
                                     user='root',
                                     password='Contraseña')

print("Connected!")
# Each list represents a row. The columns are, in order, name, description,
# X coordinate, Y coordinate, Z coordinate
devices = [['Lenovo ThinkPad P71', 'A laptop', '2', '1', '0'],
           ['Samsung Printer', 'A printer', '3', '6', '-1'],
           ['iPhone 8', 'A phone', '-2', '2', '2'],
           ['Surface Pro 8', 'A laptop', '4', '-3', '0'],
           ['Main server', 'The server', '0', '4', '1']]
# Each element represents the name of a data row
data = ['Request to mainframe', 'Respond from mainframe',
        'Printing request', 'Phone backup']
# Each list represent a relation. The elements of each list are, in order,
# the name of the sender, the name of the receiver and the name of the data.
# This needs to be changed later
connections = [['Lenovo ThinkPad P71', 'Main server', 'Request to mainframe'],
               ['Surface Pro 8', 'Main server', 'Request to mainframe'],
               ['Main server', 'Lenovo ThinkPad P71', 'Respond from mainframe'],
               ['Main server', 'Surface Pro 8', 'Respond from mainframe'],
               ['Lenovo ThinkPad P71', 'Samsung Printer', 'Printing request'],
               ['Surface Pro 8', 'Samsung Printer', 'Printing request'],
               ['Surface Pro 8', 'iPhone 8', 'Phone backup']]

cursor = connection.cursor()
for row in devices:
    sql_select_Query = "SELECT * FROM Devices Where Name = %s"
    cursor = connection.cursor()
    cursor.execute(sql_select_Query, [row[0]])
    records = cursor.fetchall()
    if len(records) == 0:
        mySql_insert_query = """INSERT INTO devices (Name, Description, X_coord,
                            Y_coord, Z_coord) VALUES (%s, %s, %s, %s, %s) """
        result = cursor.execute(mySql_insert_query, row)

for d in data:
    sql_select_Query = "SELECT * FROM Data Where Name = %s"
    cursor = connection.cursor()
    cursor.execute(sql_select_Query, [d])
    records = cursor.fetchall()
    if len(records) == 0:
        mySql_insert_query = """INSERT INTO data (Name) VALUES (%s)"""
        result = cursor.execute(mySql_insert_query, [d])

for dataConnection in connections:
    sql_select_Query = "SELECT DeviceID FROM Devices WHERE Name = %s"
    cursor = connection.cursor()
    cursor.execute(sql_select_Query, [dataConnection[0]])
    senderID = cursor.fetchall()[0][0]
    sql_select_Query = "SELECT DeviceID FROM Devices WHERE Name = %s"
    cursor = connection.cursor()
    cursor.execute(sql_select_Query, [dataConnection[1]])
    receiverID = cursor.fetchall()[0][0]
    sql_select_Query = "SELECT DataID FROM Data WHERE Name = %s"
    cursor = connection.cursor()
    cursor.execute(sql_select_Query, [dataConnection[2]])
    dataID = cursor.fetchall()[0][0]
    sql_select_Query = """SELECT * FROM SendTo Where SenderID = %s AND
                        ReceiverID = %s AND DataID = %s"""
    cursor = connection.cursor()
    cursor.execute(sql_select_Query, [senderID, receiverID, dataID])
    records = cursor.fetchall()
    if len(records) == 0:
        mySql_insert_query = """INSERT INTO sendto (SenderID, ReceiverID,
                            DataID) VALUES (%s, %s, %s) """
        result = cursor.execute(mySql_insert_query,
                                [senderID, receiverID, dataID])

connection.commit()
print("Records inserted successfully into tables")
cursor.close()
connection.close()
print("MySQL connection is closed")
