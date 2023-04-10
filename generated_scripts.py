
import ast
import random
from datetime import datetime, timedelta

import psycopg2 as psycopg2
from faker import Faker


def generate_bus_route():
    # BUSROUTE

    # generate fake data and create INSERT SQL statements BusRoute LABLES
    for i in range(1000000):
        # Generate a random hour between 10 and 21
        hour = random.randint(10, 21)

        # Generate the departure time using the random hour
        departure_time = datetime.now().replace(hour=hour, minute=0, second=0, microsecond=0)

        # Calculate the arrival time as 2 hours after the departure time
        arrival_time = departure_time + timedelta(hours=2)

        # Convert the departure time and arrival time to string formats with %H:%M
        departure_hour = departure_time.strftime('%H:%M')
        arrival_hour = arrival_time.strftime('%H:%M')

        # Print the departure and arrival times as strings
        # print(departure_hour)
        # print(arrival_hour)

        distancerandom = random.randint(10, 360)
        distance_str = str(distancerandom)

        bus_name_values = ['Red Express', 'Blue Line', 'Green Line', 'Yellow Line', 'Purple Line', 'Orange Line']

        route_type_values = ['Express', 'Local']

        bus_name = random.choice(bus_name_values)
        route_type = random.choice(route_type_values)

        sql = "INSERT INTO bus_route (arrival_hour, bus_name,departure_hour, distance, route_type) VALUES ('{}', '{}', '{}', '{}', '{}');".format(
            arrival_hour, bus_name, departure_hour, distance_str, route_type, )
        sqls.append(sql)

    # write SQL statements to file in /tmp directory

    filename = 'busroute.sql'
    with open(r'D:\VSCode_ProiecteREACT\busroute.sql',
              'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))


def generate_person():
    # PERSON TABLES
    for i in range(1000000):
        first_name = fake.first_name()
        gender_values = ["Female", "Male"]
        gender = random.choice(gender_values)
        last_name = fake.last_name()
        nationality_values = ['American', 'British', 'Canadian', 'German', 'French', 'Italian', 'Chinese', 'Japanese',
                              'Mexican', 'Brazilian', 'Romania']
        nationality = random.choice(nationality_values)

        phone_number = '0' + fake.numerify(text='#########')
        # print(phone_number)

        sql = "INSERT INTO person (first_name, gender, last_name, nationality, phone_number) VALUES ('{}', '{}', '{}', '{}', '{}');".format(
            first_name, gender, last_name, nationality, phone_number)
        sqls.append(sql)

        # write SQL statements to file in /tmp directory

    filename = 'person.sql'
    with open(r'D:\VSCode_ProiecteREACT\person.sql',
              'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))


def generate_luggage():
    # LUGGAGE ~~~~~~~~~~~~~~~~~~~~~~~~~

    # get the IDs of the record labels Person in the database
    cur.execute("SELECT person_id FROM person")
    # rec_lbl_ids = cur.fetchall()
    person_ids = cur.fetchall()

    # generate fake data and create INSERT SQL statements LUGGAGE
    # LUGGAGE  :13
    for i in range(1000000):
        color_values = ['Red', 'Orange', 'Yellow', 'Green', 'Blue', 'Purple', 'Pink', 'Black', 'White', 'Gray']
        color = random.choice(color_values)
        priority_values = ['High', 'Medium', 'Low']
        priority = random.choice(priority_values)
        status_values = ['Checked', 'Unchecked']
        status = random.choice(status_values)
        type_values = ['Backpack', 'Suitcase']
        type = random.choice(type_values)
        weight = random.randint(1, 80)

        # rec_lbl_id = fake.random.choice(rec_lbl_ids)[0]

        person_id = fake.random.choice(person_ids)[0]

        sql = "INSERT INTO luggage (color, priority, status, type, weight, person_id ) VALUES ('{}', '{}', '{}', '{}', {}, {});".format(
            color, priority, status, type, weight, person_id)

        sqls.append(sql)

    # write SQL statements to file in /tmp directory

    filename = 'luggage.sql'
    with open(r'D:\VSCode_ProiecteREACT\luggage.sql', 'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))


def generate_ticket():
    # TICKET ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    # Person --> Ticket <--- BusRoute
    # Singer -->Albums < --- Group

    # generate fake data and create INSERT SQL statements ALBUMS

    cur.execute("SELECT person_id FROM person")
    id_person_multiple = cur.fetchall()

    cur.execute("SELECT bus_route_id FROM bus_route")
    id_busroute_multiple = cur.fetchall()

    # cur.execute("SELECT ARRAY(SELECT ROW(bus_route_id, person_id) FROM ticket)::text FROM ticket;") #???
    # result = cur.fetchone()[0]
    # existing_pairs = ast.literal_eval(result)
    # print(existing_pairs)

    existing_pairs = set()

    # create a list of albums with unique foreign key pair
    # albums=[]
    # singer_group_pairs=set()

    for i in range(10000000):
        person = random.choice(id_person_multiple)
        busroute = random.choice(id_busroute_multiple)
        pair = (busroute, person)

        while pair in existing_pairs:
            person = random.choice(id_person_multiple)
            busroute = random.choice(id_busroute_multiple)
            pair = (busroute, person)

        existing_pairs.add(pair)

        id_busroute = pair[0]
        id_busroute_without_parentheses = str(id_busroute).replace('(', '').replace(')', '').replace(',', '')

        id_person = pair[1]
        id_person_without_parentheses = str(id_person).replace('(', '').replace(')', '').replace(',', '')

        # print(id_person_without_parentheses)

        payment_method_values = ['Card', 'Cash']
        payment_method = random.choice(payment_method_values)

        seat_number = random.randint(1, 81)

        sql = "INSERT INTO ticket (payment_method, seat_number, bus_route_id, person_id) VALUES ('{}', '{}', {}, {});".format(
            payment_method, seat_number, id_busroute_without_parentheses, id_person_without_parentheses)

        sqls.append(sql)

        # write SQL statements to file in /tmp directory

    filename = 'ticket.sql'
    with open(r'D:\VSCode_ProiecteREACT\ticket.sql', 'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    #fake = Faker()

    # create list to store SQL statements
    sqls = []

    conn = psycopg2.connect(
        host="localhost",
        database="bus2db",
        user="postgres",
        password="sabina",
        port=5433
    )

    # create a cursor to interact with the database
    cur = conn.cursor()

    # create a Faker object
    fake = Faker()





    # BUSROUTE
    '''
    # generate fake data and create INSERT SQL statements BusRoute LABLES
    for i in range(1000000):
        # Generate a random hour between 10 and 21
        hour = random.randint(10, 21)

        # Generate the departure time using the random hour
        departure_time = datetime.now().replace(hour=hour, minute=0, second=0, microsecond=0)

        # Calculate the arrival time as 2 hours after the departure time
        arrival_time = departure_time + timedelta(hours=2)

        # Convert the departure time and arrival time to string formats with %H:%M
        departure_hour = departure_time.strftime('%H:%M')
        arrival_hour = arrival_time.strftime('%H:%M')

        # Print the departure and arrival times as strings
        # print(departure_hour)
        # print(arrival_hour)

        # address = fake.address()
        # name_rl = fake.name()
        # nr_collaborations = random.randint(1, 200)
        # price = random.randint(50, 2000)
        # review_values = ['Ok', 'Bad', 'VeryBad', 'Amazing', "Awful", "Wonderful", "Perfect"]
        distancerandom = random.randint(10, 360)
        distance_str = str(distancerandom)

        bus_name_values = ['Red Express', 'Blue Line', 'Green Line', 'Yellow Line', 'Purple Line', 'Orange Line']

        route_type_values = ['Express', 'Local']

        # Generate a random review
        # review = random.choice(review_values)

        bus_name = random.choice(bus_name_values)
        route_type = random.choice(route_type_values)

        sql = "INSERT INTO bus_route (arrival_hour, bus_name,departure_hour, distance, route_type) VALUES ('{}', '{}', '{}', '{}', '{}');".format(
            arrival_hour, bus_name, departure_hour, distance_str, route_type, )
        sqls.append(sql)

    # write SQL statements to file in /tmp directory

    filename = 'busroute.sql'
    with open(r'D:\VSCode_ProiecteREACT\busroute.sql',
              'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))
    '''

    # PERSON TABLES
    '''
    for i in range(1000000):

        first_name=fake.first_name()
        gender_values=["Female", "Male"]
        gender=random.choice(gender_values)
        last_name=fake.last_name()
        nationality_values = ['American', 'British', 'Canadian', 'German', 'French', 'Italian', 'Chinese', 'Japanese',
                              'Mexican', 'Brazilian', 'Romania']
        nationality=random.choice(nationality_values)

        phone_number = '0' + fake.numerify(text='#########')
        #print(phone_number)

        sql = "INSERT INTO person (first_name, gender, last_name, nationality, phone_number) VALUES ('{}', '{}', '{}', '{}', '{}');".format(
            first_name, gender, last_name, nationality, phone_number)
        sqls.append(sql)

        # write SQL statements to file in /tmp directory

    filename = 'person.sql'
    with open(r'D:\VSCode_ProiecteREACT\person.sql',
              'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))
    '''

    # LUGGAGE ~~~~~~~~~~~~~~~~~~~~~~~~~

    '''
    # get the IDs of the record labels Person in the database
    cur.execute("SELECT person_id FROM person")
    #rec_lbl_ids = cur.fetchall()
    person_ids = cur.fetchall()


    # generate fake data and create INSERT SQL statements LUGGAGE
    # LUGGAGE  :13
    for i in range(1000000):
        color_values = ['Red', 'Orange', 'Yellow', 'Green', 'Blue', 'Purple', 'Pink', 'Black', 'White', 'Gray']
        color=random.choice(color_values)
        priority_values=['High', 'Medium', 'Low']
        priority=random.choice(priority_values)
        status_values=['Checked','Unchecked']
        status=random.choice(status_values)
        type_values=['Backpack', 'Suitcase']
        type=random.choice(type_values)
        weight = random.randint(1, 80)

        #rec_lbl_id = fake.random.choice(rec_lbl_ids)[0]


        person_id = fake.random.choice(person_ids)[0]


        sql = "INSERT INTO luggage (color, priority, status, type, weight, person_id ) VALUES ('{}', '{}', '{}', '{}', {}, {});".format(color, priority, status, type, weight, person_id)

        sqls.append(sql)

    # write SQL statements to file in /tmp directory

    filename = 'luggage.sql'
    with open(r'D:\VSCode_ProiecteREACT\luggage.sql', 'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))
    '''

    # TICKET ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    # Person --> Ticket <--- BusRoute
    # Singer -->Albums < --- Group

    '''
    # generate fake data and create INSERT SQL statements ALBUMS

    cur.execute("SELECT person_id FROM person")
    id_person_multiple = cur.fetchall()

    cur.execute("SELECT bus_route_id FROM bus_route")
    id_busroute_multiple = cur.fetchall()

    # cur.execute("SELECT ARRAY(SELECT ROW(bus_route_id, person_id) FROM ticket)::text FROM ticket;") #???
    # result = cur.fetchone()[0]
    # existing_pairs = ast.literal_eval(result)
    # print(existing_pairs)

    existing_pairs=set()

    #create a list of albums with unique foreign key pair
    #albums=[]
    #singer_group_pairs=set()



    for i in range(10000000):
        person=random.choice(id_person_multiple)
        busroute=random.choice(id_busroute_multiple)
        pair=(busroute, person)

        while pair in existing_pairs:
            person = random.choice(id_person_multiple)
            busroute = random.choice(id_busroute_multiple)
            pair=(busroute, person)

        existing_pairs.add(pair)

        id_busroute = pair[0]
        id_busroute_without_parentheses = str(id_busroute).replace('(', '').replace(')', '').replace(',', '')

        id_person = pair[1]
        id_person_without_parentheses = str(id_person).replace('(', '').replace(')', '').replace(',', '')

        #print(id_person_without_parentheses)


        album_name=fake.domain_word()
        payment_method_values=['Card','Cash']
        payment_method=random.choice(payment_method_values)

        seat_number=random.randint(1,81)



        sql = "INSERT INTO ticket (payment_method, seat_number, bus_route_id, person_id) VALUES ('{}', '{}', {}, {});".format(payment_method, seat_number, id_busroute_without_parentheses, id_person_without_parentheses)

        sqls.append(sql)

        # write SQL statements to file in /tmp directory

    filename = 'ticket.sql'
    with open(r'D:\VSCode_ProiecteREACT\ticket.sql', 'w') as f:
        for i, sql in enumerate(sqls):
            f.write(sql + '\n')
            # print('Inserted record {} of 100'.format(i + 1))
    '''
