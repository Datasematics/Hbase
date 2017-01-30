from starbase import Connection

#create a connection to hbase rest server default rest port is 8080
c = Connection(host='103.233.79.140', port=8080)

#For listing table
c.tables()
#connecting to existing table and performing operations
t = c.table(‘mytable’)
#inserting to the table on successful insert we get the return code as 200
t.insert(
   'my-key-1',
   {
     'cf1': {'key11': 'value 11', 'key12': 'value 12',
                   'key13': 'value 13'},
      'cf2': {'key21': 'value 21', 'key22': 'value 22'},
      'cf3': {'key32': 'value 31', 'key32': 'value 32'}
   }
   )
#updating the row
t.update(
    'my-key-1',
    {'cf1': {'key41': 'value 41', 'key42': 'value 42'}}
    )

# removing the row 
t.remove('my-key-1', 'cf1', 'key41')

