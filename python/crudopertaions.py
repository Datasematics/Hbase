import happybase

connection = happybase.Connection(‘hmaster ipaddress’)

# before first use:
connection.open()

#The Connection class provides the main entry point to interact with HBase. For #instance, to list the available tables, use Connection.tables():

print(connection.tables())

#creating tables using connection object


connection.create_table(
    'mytable',
    {'cf1': dict(max_versions=10),
     'cf2': dict(max_versions=1, block_cache_enabled=False),
     'cf3': dict(),  # use defaults
    }
)

#For querying or inserting the data we should obtain the the table instance
 to work with. Simply call Connection.table(), passing it the table name:

table = connection.table('mytable')
#putting data into the table

table.put(b'row-key', {b'cf1:qual1': b'value1',
                       b'cf2:qual2': b'value2'})


#reterving data based on row key

>>> row = table.row(b'row-key')
>>> print(row[b'cf1:qual1'])

#complete table scan 

for key, data in table.scan(row_prefix=b'row'):
     print(key, data)
