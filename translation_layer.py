import uuid

import mysql.connector
import psycopg2

# MySQL connection parameters
mysql_config = {
    'host': 'localhost',  # Replace with your MySQL host
    'port': 3306,
    'user': 'root',  # Replace with your MySQL username
    'password': '****',  # Replace with your MySQL password
    'database': 'orar2015'  # Replace with your MySQL database name
}

# PostgreSQL connection parameters
postgres_config = {
    'host': 'localhost',
    'port': 9876,
    'user': 'postgres',  # Replace with your PostgreSQL username
    'password': '****',  # Replace with your PostgreSQL password
    'database': 'postgres'  # Replace with your PostgreSQL database name
}

# Connect to MySQL database
try:
    mysql_conn = mysql.connector.connect(**mysql_config)
    mysql_cursor = mysql_conn.cursor(dictionary=True)
    print("Connected to MySQL database.")
except mysql.connector.Error as err:
    print(f"Error connecting to MySQL: {err}")
    exit(1)

# Connect to PostgreSQL database
try:
    postgres_conn = psycopg2.connect(**postgres_config)
    postgres_cursor = postgres_conn.cursor()
    print("Connected to PostgreSQL database.")
except psycopg2.Error as err:
    print(f"Error connecting to PostgreSQL: {err}")
    exit(1)


def translate_academic_ranks():
    query = "select * from orar2015.posturi"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_all_academic_ranks_and_english_localized_names(data)


def new_insert_all_academic_ranks_and_english_localized_names(data):
    new_insert_all_academic_ranks(data)
    new_insert_all_academic_ranks_locale(data)


def new_insert_all_academic_ranks(data):
    # data: {'id': 1, 'nume': 'Prof.', 'denr': 'Profesor Universitar', 'dene': 'Professor', 'pri': 3}
    postgres_cursor.execute("DELETE FROM teacher")
    postgres_cursor.execute("DELETE FROM academic_rank_locale")
    postgres_cursor.execute("DELETE FROM academic_rank")
    query_academic_rank = """ INSERT INTO academic_rank (academic_rank_id, rank_name)
        VALUES (%s, %s)"""
    try:
        for row in data:
            academic_rank_id = row.get("id")
            rank_name = row.get("denr")

            postgres_cursor.execute(query_academic_rank, (academic_rank_id, rank_name))
            print(f"Inserted: {academic_rank_id}, {rank_name}")

        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()


def new_insert_all_academic_ranks_locale(data):
    # data: {'id': 1, 'nume': 'Prof.', 'denr': 'Profesor Universitar', 'dene': 'Professor', 'pri': 3}
    postgres_cursor.execute("DELETE FROM teacher")
    postgres_cursor.execute("DELETE FROM academic_rank_locale")
    query_academic_rank_locale = """ INSERT INTO academic_rank_locale (academic_rank_id, language_tag, academic_rank_locale_name, academic_rank_abbreviation_locale_name)
            VALUES (%s, %s, %s, %s)"""
    try:
        for row in data:
            academic_rank_id = row.get("id")
            rank_name_romanian = row.get("denr")
            rank_name_abbreviation_romanian = row.get("nume")

            postgres_cursor.execute(query_academic_rank_locale,
                                    (academic_rank_id, "ro-RO", rank_name_romanian, rank_name_abbreviation_romanian))

            if len(row.get("dene")) != 0:
                rank_name_english = row.get("dene")
                postgres_cursor.execute(query_academic_rank_locale, (
                    academic_rank_id, "en-GB", rank_name_english, rank_name_abbreviation_romanian))

        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()


# translate_academic_ranks()

def translate_teachers():
    # {'id': 86, 'nume': 'FRENTIU Militon', 'emailc': '', 'emaild': '', 'web': '', 'domeniir': '', 'domeniie': '', 'catedra': 2, 'post': 1, 'dr': 1, 'activ': 1, 'cod': 'frmi', 'adresa': '', 'tel': '5807', 'restrictii': None}
    query = "select * from cadre"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_teachers(data)

def new_insert_teachers(data):
    postgres_cursor.execute("DELETE FROM teacher")
    query_teacher = """ INSERT INTO teacher (teacher_id, academic_rank_id, first_name, surname, code_name) VALUES (%s, %s, %s, %s, %s)"""

    try:

        for row in data:
            academic_rank_id = row.get("post")
            full_name = row.get("nume").split(' ', 1)
            first_name = full_name[0]
            surname = ""
            if len(full_name) > 1:
                surname = full_name[1]
            code_name = row.get("cod")
            uuid_code = str(uuid.uuid5(uuid.NAMESPACE_URL, name=str(full_name)))
            # print(uuid_code)
            postgres_cursor.execute(query_teacher, (uuid_code, academic_rank_id, first_name, surname, code_name))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# translate_teachers()

def translate_day_definitions_and_localization():
    # postgres_cursor.execute("DELETE FROM class_instan")
    postgres_cursor.execute("DELETE FROM day_definition_locale")
    postgres_cursor.execute("DELETE FROM day_definition")
    query = """SELECT * FROM zile"""
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_days(data)

def new_insert_days(data):
    # {'id': 1, 'cod': 'Lu', 'denr': 'Luni'}
    day_query = """INSERT INTO day_definition(day_definition_id, day_name) VALUES (%s, %s)"""
    day_localized = """INSERT INTO day_definition_locale(day_definition_id, language_tag, day_name_locale) VALUES (%s, %s, %s)"""
    try:
        for row in data:
            day_id = row.get("id")
            day_code_name = row.get("cod")
            day_name_romanian = row.get("denr")
            # TODO: day name english

            postgres_cursor.execute(day_query, (day_id, day_code_name))
            postgres_cursor.execute(day_localized, (day_id, "ro-RO", day_name_romanian))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# translate_day_definitions_and_localization()

def translate_academic_specialization_and_localization():
    query = "SELECT * FROM specorar"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_academic_specializations_and_localization(data)

def new_insert_academic_specializations_and_localization(data):
    # {'id': 10, 'nivel': 'L', 'denr': 'Matematica - linia de studiu romana', 'dene': 'Mathematics - in Romanian', 'pri': 1, 'denscurt': 'Mat.'}
    academic_specialization_query = """INSERT INTO academic_specialization(academic_specialization_id, internal_name) VALUES (%s, %s)"""
    academic_specialization_locale_query = """INSERT INTO academic_specialization_locale(academic_specialization_id, language_tag, level, name, name_abbreviated) VALUES (%s, %s, %s, %s, %s)"""
    try:
        for row in data:
            academic_specialization_id = row.get("id")
            internal_name = row.get("denr")
            romanian_name = row.get("denr")
            english_name = row.get("dene")
            level = row.get("nivel")
            shortened_name_romanian = row.get("denscurt")
            postgres_cursor.execute(academic_specialization_query, (academic_specialization_id, internal_name))
            postgres_cursor.execute(academic_specialization_locale_query, (academic_specialization_id, "ro-RO", level, romanian_name, shortened_name_romanian))
            if len(english_name):
                postgres_cursor.execute(academic_specialization_locale_query, (academic_specialization_id, "en-GB", level, english_name, shortened_name_romanian))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# translate_academic_specialization_and_localization()


def translate_formations():
    query  = "SELECT * FROM formatii"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_formations(data)

def new_insert_formations(data):
    # {'id': 247, 'sectia': 11, 'an': 3, 'cod': 'MI3', 'denumire': '', 'nivel': 1, 'componenta': '331;332;333;334;'},
    formation_query = """INSERT INTO formation(formation_id, code, components, formation_level, year, academic_specialization_id) VALUES (%s, %s, %s, %s, %s, %s)"""
    try:
        for row in data:
            code = row.get("cod")
            formation_id = str(uuid.uuid5(uuid.NAMESPACE_URL, name=str(code)))
            components = row.get("componenta")
            formation_level = row.get("nivel")
            year = row.get("an")
            academic_specialization_id = row.get("sectia")

            postgres_cursor.execute(formation_query, (formation_id, code, components, formation_level, year, academic_specialization_id))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# translate_formations()

def translate_rooms():
    query = "SELECT * FROM sali"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_rooms(data)

def new_insert_rooms(data):
    #{'id': 1, 'cod': '2/I', 'locuri': 168, 'legenda': 'Sala Nicolae Iorga, Cladirea Centrala, etaj 1 (Str. M. Kogalniceanu)', 'cladire': 1, 'status': 1, 'restrictii': '000000000000000000000000000000000000000000000000000000000000000000000000'}
    room_query = """INSERT INTO room(room_id, name, address) VALUES (%s, %s, %s)"""
    try:
        for row in data:
            room_id = row.get("id")
            code = row.get("cod")
            address = row.get("legenda")

            postgres_cursor.execute(room_query, (room_id, code, address))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# translate_rooms()

def translate_course_code_names_and_instances():
    query = "SELECT * FROM disc"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_course_code_names_and_instances(data)

def new_insert_course_code_names_and_instances(data):
    # {'id': 1, 'cod': 'LLU0011', 'denr': 'Limba engleza (1)', 'dene': ''}
    course_code_name_query = """INSERT INTO course_code_name(course_codename_id, course_name, course_name_abbreviaton) VALUES (%s, %s, %s)"""
    course_code_name_locale_query = """INSERT INTO course_code_name_locale(course_codename_id, language_tag, course_name_locale, course_name_abbreviation_locale) VALUES (%s, %s, %s, %s)"""
    course_instance_query = """INSERT INTO course_instance(course_instance_id, course_code, course_id) VALUES (%s, %s, %s)"""
    try:
        last_course_name = ''
        for row in data:
            course_codename_id = row.get("id")
            course_name = row.get("denr")
            course_code = row.get("cod")
            last_course_name = course_name
            postgres_cursor.execute(course_code_name_query, (course_codename_id, course_name, ''))
            postgres_cursor.execute(course_code_name_locale_query, (course_codename_id, "ro-RO", course_name, ''))
            # TODO: change this because in some cases there are multiple courses with the same code for some reason?? => make it random but salted with an attribute
            course_instance_id = str(uuid.uuid4())
            postgres_cursor.execute(course_instance_query, (course_instance_id, course_code, course_codename_id))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        print(f"{last_course_name}")
        postgres_conn.rollback()

# translate_course_code_names_and_instances()

def insert_class_types():
    class_type_query = """INSERT INTO class_type(class_type_id, class_type) VALUES (%s, %s)"""
    class_type_locale_query = """INSERT INTO class_type_locale(class_type_id, language_tag, class_type_locale) VALUES (%s, %s, %s)"""
    try:
        postgres_cursor.execute(class_type_query, (1, 'Curs'))
        postgres_cursor.execute(class_type_query, (2, 'Seminar'))
        postgres_cursor.execute(class_type_query, (3, 'Laborator'))

        postgres_cursor.execute(class_type_locale_query, (1, 'ro-RO', "Curs"))
        postgres_cursor.execute(class_type_locale_query, (2, 'ro-RO', "Seminar"))
        postgres_cursor.execute(class_type_locale_query, (3, 'ro-RO', "Laborator"))

        postgres_cursor.execute(class_type_locale_query, (1, 'en-GB', "Course"))
        postgres_cursor.execute(class_type_locale_query, (2, 'en-GB', "Seminar"))
        postgres_cursor.execute(class_type_locale_query, (3, 'en-GB', "Laboratory"))
        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# insert_class_types()

def translate_class_instances():
    query = "SELECT * FROM repart"
    mysql_cursor.execute(query)
    data = mysql_cursor.fetchall()
    new_insert_class_instances(data)

def new_insert_class_instances(data):
    # 'id': 1, 'disciplina': 'MME3115', 'tipactiv': 'C', 'nrore': 2, 'formatia': 'MaMAv2', 'sem': 2, 'cuplat': 0, 'tiprep': 0, 'persoana': 'komi', 'sala': 'e', 'zi': 'Lu', 'ora_i': 7, 'ora_s': 8, 'proiector': 0}
    class_instance_query = """INSERT INTO class_instance(class_id, class_type_id, course_instance_id, class_day_id, formation_id, room_id, teacher_id, start_hour, end_hour, frequency) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"""
    try:
        for row in data:
            class_id = str(uuid.uuid5(uuid.NAMESPACE_URL, name=str(row.get("id"))))
            class_type_id = row.get("tipactiv")
            if class_type_id == 'C':
                class_type_id = '1'
            elif class_type_id == 'S':
                class_type_id = '2'
            elif class_type_id == 'L':
                class_type_id = '3'

            get_course_instance_id_query = """SELECT course_instance.course_instance_id FROM course_instance WHERE course_code = %s"""
            course_code = row.get("disciplina")
            postgres_cursor.execute(get_course_instance_id_query, (course_code,))
            course_instance_id = postgres_cursor.fetchone()[0]

            get_class_day_id_query = """select day_definition.day_definition_id from day_definition where day_name=%s"""
            class_day_id = row.get("zi")
            postgres_cursor.execute(get_class_day_id_query, (class_day_id,))
            class_day_id = postgres_cursor.fetchone()[0]

            get_formation_id_query = """select formation_id from formation where code=%s"""
            formation = row.get("formatia")
            postgres_cursor.execute(get_formation_id_query, (formation,))
            formation_id = postgres_cursor.fetchone()[0]

            get_room_id_query = """select room_id from room where name=%s"""
            room = row.get("sala")
            postgres_cursor.execute(get_room_id_query, (room,))
            room_id = postgres_cursor.fetchone()[0]

            get_teacher_id_query = """select teacher_id from teacher where code_name=%s"""
            teacher = row.get("persoana")
            postgres_cursor.execute(get_teacher_id_query, (teacher,))
            teacher_id = postgres_cursor.fetchone()[0]

            start_hour = row.get("ora_i")
            end_hour = str(int(row.get("ora_s")) + 1)
            frequency = row.get("tiprep")

            postgres_cursor.execute(class_instance_query, (class_id, class_type_id, course_instance_id, class_day_id, formation_id, room_id, teacher_id, start_hour, end_hour, frequency))

        postgres_conn.commit()
        print("Data inserted into PostgreSQL successfully.")
    except psycopg2.Error as err:
        print(f"Error inserting data into PostgreSQL: {err}")
        postgres_conn.rollback()

# translate_class_instances()
