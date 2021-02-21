create or replace NONEDITIONABLE PACKAGE BODY ERP_CLOTHING_FACTORY AS

----------------------------------- Products --------------------------------------------

    FUNCTION get_product_by_id(
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER AS

    BEGIN
        APEX_JSON.initialize_clob_output();


        FOR prod IN (
            SELECT *
            FROM products
            WHERE id = in_id AND status <> STATUS_DELETED
        ) LOOP
            create_product_response(
                prod.id,
                prod.name,
                prod.category,
                prod.quality,
                prod.price,
                prod.image,
                prod.status,
                prod.creation_date
            );
        END LOOP;

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_product_by_id;

-------------------------------------------------------------------------------

    FUNCTION get_all_products (
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
        APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

        FOR prod IN (
            SELECT *
            FROM products
            WHERE status <> STATUS_DELETED
        ) LOOP
            create_product_response(
                prod.id,
                prod.name,
                prod.category,
                prod.quality,
                prod.price,
                prod.image,
                prod.status,
                prod.creation_date
            );
        END LOOP;

        APEX_JSON.close_array();

        response := APEX_JSON.get_clob_output();
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_all_products;

-------------------------------------------------------------------------------

    FUNCTION update_product (
        in_id IN VARCHAR2,
        in_product IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_category VARCHAR2(256);
        v_quality VARCHAR2(256);
        v_price NUMBER;
        v_image VARCHAR2(256);
        v_status VARCHAR2(256);

        v_result INTEGER;

        v_index INTEGER := 1;

        v_employee_id VARCHAR2(32);
        v_store_id VARCHAR2(32);
        v_type_id VARCHAR2(32);

        v_exist INTEGER;

        v_old_status VARCHAR2(256);
    BEGIN
        SELECT COUNT(id)
        INTO v_exist
        FROM products
        WHERE id = in_id AND status <> STATUS_DELETED;

        IF v_exist = 0 THEN
            response := get_error_object(ERROR_400,ERROR_PRODUCT_NOT_FOUND);
            RETURN RESULT_NOK;
        END IF;

        SELECT status
        INTO v_old_status
        FROM products
        WHERE id = in_id;

        IF v_old_status = STATUS_SOLD THEN
            response := get_error_object(ERROR_400,ERROR_STATUS_SOLD);
            RETURN RESULT_NOK;
        END IF;

        -- Get all Details
        APEX_JSON.parse(in_product);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_category := APEX_JSON.get_varchar2(p_path => 'category');
            v_quality := APEX_JSON.get_varchar2(p_path => 'quality');
            v_price := APEX_JSON.get_varchar2(p_path => 'price');
            v_image := APEX_JSON.get_varchar2(p_path => 'image');
            v_status := APEX_JSON.get_varchar2(p_path => 'status');
            v_type_id := APEX_JSON.get_varchar2(p_path => 'type.id');
            v_store_id := APEX_JSON.get_varchar2(p_path => 'store.id');
        APEX_JSON.free_output();

        IF v_status = STATUS_SOLD THEN
            IF v_store_id IS NULL THEN
                response := get_error_object(ERROR_400,ERROR_STORE_NULL);
                RETURN RESULT_NOK;
            END IF;
            UPDATE products
            SET status = v_status, store_id = v_store_id
            WHERE id = in_id;
        ELSIF v_status = STATUS_DELETED THEN
            UPDATE products
            SET status = v_status
            WHERE id = in_Id;

            APEX_JSON.initialize_clob_output();
                APEX_JSON.open_object();
                    APEX_JSON.write('status',UPPER(STATUS_DELETED));
                APEX_JSON.close_object();
            response := APEX_JSON.get_clob_output();
            RETURN RESULT_OK;

        ELSE
            UPDATE products
            SET name = v_name, category = v_category, quality = v_quality, price = v_price, image = v_image, type_id = v_type_id
            WHERE id = in_id;

            -- Delete old employees
            DELETE FROM product_employee
            WHERE product_id = in_id;

            -- Insert new employees
            APEX_JSON.parse(in_product);
                WHILE APEX_JSON.get_varchar2 (p_path => 'employees[%d].id', p0 => v_index) IS NOT NULL
                LOOP
                    v_employee_id := APEX_JSON.get_varchar2 (p_path => 'employees[%d].id', p0 => v_index);

                    INSERT INTO product_employee (
                        product_id,
                        employee_id
                    ) VALUES (
                        in_id,
                        v_employee_id
                    );
                    v_index := v_index + 1;
                END LOOP;
            APEX_JSON.free_output();
        END IF;

        v_result := get_product_by_id (
            in_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END update_product;

-------------------------------------------------------------------------------

    FUNCTION create_product (
        in_product IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_category VARCHAR2(256);
        v_quality VARCHAR2(256);
        v_price NUMBER;
        v_image VARCHAR2(256);
        v_status VARCHAR2(256);

        v_inserted_id VARCHAR2(256);

        v_result INTEGER;

        v_index INTEGER := 1;

        v_employee_id VARCHAR2(32);
        v_type_id VARCHAR2(32);
    BEGIN
        APEX_JSON.parse(in_product);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_category := APEX_JSON.get_varchar2(p_path => 'category');
            v_quality := APEX_JSON.get_varchar2(p_path => 'quality');
            v_price := APEX_JSON.get_varchar2(p_path => 'price');
            v_image := APEX_JSON.get_varchar2(p_path => 'image');
            v_status := APEX_JSON.get_varchar2(p_path => 'status');
            v_type_id := APEX_JSON.get_varchar2(p_path => 'type.id');
        APEX_JSON.free_output();

        APEX_JSON.initialize_clob_output();

        INSERT INTO products (
            name,
            category,
            quality,
            price,
            image,
            status,
            type_id
        ) VALUES (
            v_name,
            v_category,
            v_quality,
            v_price,
            v_image,
            STATUS_ACTIVE,
            v_type_id
        ) RETURNING ID INTO v_inserted_id;

        APEX_JSON.parse(in_product);
            WHILE APEX_JSON.get_varchar2 (p_path => 'employees[%d].id', p0 => v_index) IS NOT NULL
            LOOP
                v_employee_id := APEX_JSON.get_varchar2 (p_path => 'employees[%d].id', p0 => v_index);

                INSERT INTO product_employee (
                    product_id,
                    employee_id
                ) VALUES (
                    v_inserted_id,
                    v_employee_id
                );
                v_index := v_index + 1;
            END LOOP;
        APEX_JSON.free_output();

        v_result := get_product_by_id(
            v_inserted_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END create_product;

-------------------------------------------------------------------------------

    FUNCTION get_products_by_employee(
        in_employee_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER AS

    BEGIN
        APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

        FOR prod IN (
            SELECT p.*
            FROM products p
            JOIN product_employee pe ON p.id = pe.product_id
            WHERE pe.employee_id = in_employee_id AND p.status <> STATUS_DELETED
        ) LOOP
            create_product_response(
                prod.id,
                prod.name,
                prod.category,
                prod.quality,
                prod.price,
                prod.image,
                prod.status,
                prod.creation_date
            );
        END LOOP;

        APEX_JSON.close_array();

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_products_by_employee;

-------------------------------------------------------------------------------

    FUNCTION get_sold_products(
        in_store_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER AS
    BEGIN
        APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

        IF in_store_id = 'all' THEN
            FOR prod IN (
                SELECT *
                FROM products
                WHERE store_id IS NOT NULL
            ) LOOP
                create_product_response(
                    prod.id,
                    prod.name,
                    prod.category,
                    prod.quality,
                    prod.price,
                    prod.image,
                    prod.status,
                    prod.creation_date
                );
            END LOOP;
        ELSE
            FOR prod IN (
                SELECT *
                FROM products
                WHERE store_id = in_store_id
            ) LOOP
                create_product_response(
                    prod.id,
                    prod.name,
                    prod.category,
                    prod.quality,
                    prod.price,
                    prod.image,
                    prod.status,
                    prod.creation_date
                );
            END LOOP;
        END IF;

        APEX_JSON.close_array();

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,sqlerrm);
            RETURN RESULT_NOK;
    END get_sold_products;

----------------------------------- Employees --------------------------------------------

    FUNCTION get_employee_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
      APEX_JSON.initialize_clob_output();


        FOR emp IN (
            SELECT *
            FROM employees
            WHERE id = in_id
        ) LOOP
            create_employee_response(
                emp.id,
                emp.name,
                emp.phone_number
            );
        END LOOP;

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_employee_by_id;

-------------------------------------------------------------------------------

    FUNCTION get_all_employees (
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
      APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

        FOR emp IN (
            SELECT *
            FROM employees
        ) LOOP
            create_employee_response(
                emp.id,
                emp.name,
                emp.phone_number
            );
        END LOOP;
        APEX_JSON.close_array();
        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_all_employees;

-------------------------------------------------------------------------------

    FUNCTION update_employee (
        in_id IN VARCHAR2,
        in_employee IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_phone_number VARCHAR2(64);
        v_role_id VARCHAR2(32);
        v_department_id VARCHAR2(32);

        v_result INTEGER;

        v_exist INTEGER;
    BEGIN
        SELECT COUNT(id)
        INTO v_exist
        FROM employees
        WHERE id = in_id;

        IF v_exist = 0 THEN
            response := get_error_object(ERROR_400,ERROR_EMPLOYEE_NOT_FOUND);
            RETURN RESULT_NOK;
        END IF;

        APEX_JSON.parse(in_employee);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_phone_number := APEX_JSON.get_varchar2(p_path => 'phone_number');
            v_role_id := APEX_JSON.get_varchar2(p_path => 'role.id');
            v_department_id := APEX_JSON.get_varchar2(p_path => 'department.id');
        APEX_JSON.free_output();

        UPDATE employees
        SET name = v_name, phone_number = v_phone_number, role_id = v_role_id, department_id = v_department_id
        WHERE id = in_id;

        v_result := get_employee_by_id (
            in_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END update_employee;

-------------------------------------------------------------------------------

    FUNCTION create_employee (
        in_employee IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_phone_number VARCHAR2(64);
        v_role_id VARCHAR2(32);
        v_department_id VARCHAR2(32);

        v_inserted_id VARCHAR2(32);

        v_result INTEGER;
    BEGIN
        APEX_JSON.parse(in_employee);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_phone_number := APEX_JSON.get_varchar2(p_path => 'phone_number');
            v_role_id := APEX_JSON.get_varchar2(p_path => 'role.id');
            v_department_id := APEX_JSON.get_varchar2(p_path => 'department.id');
        APEX_JSON.free_output();

        INSERT INTO employees (
            name,
            phone_number,
            role_id,
            department_id
        ) VALUES (
            v_name,
            v_phone_number,
            v_role_id,
            v_department_id
        ) RETURNING ID INTO v_inserted_id;

        v_result := get_employee_by_id (
            v_inserted_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END create_employee;

----------------------------------- Department --------------------------------------------

    FUNCTION get_department_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
      APEX_JSON.initialize_clob_output();


        FOR dep IN (
            SELECT *
            FROM departments
            WHERE id = in_id
        ) LOOP
            create_department_response(
                dep.id,
                dep.name
            );
        END LOOP;

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_department_by_id;

-------------------------------------------------------------------------------

    FUNCTION get_all_departments (
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
      APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

        FOR dep IN (
            SELECT *
            FROM departments
        ) LOOP
            create_department_response(
                dep.id,
                dep.name
            );
        END LOOP;
        APEX_JSON.close_array();

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_all_departments;

-------------------------------------------------------------------------------

    FUNCTION update_department (
        in_id IN VARCHAR2,
        in_department IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_manager_id VARCHAR2(32);

        v_result INTEGER;
    BEGIN
        APEX_JSON.parse(in_department);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_manager_id := APEX_JSON.get_varchar2(p_path => 'manager.id');
        APEX_JSON.free_output();

        UPDATE departments
        SET name = v_name, manager_id = v_manager_id
        WHERE id = in_id;

        v_result := get_department_by_id (
            in_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END update_department;

-------------------------------------------------------------------------------

    FUNCTION create_department (
        in_department IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_manager_id VARCHAR2(32);

        v_inserted_id VARCHAR2(32);

        v_result INTEGER;
    BEGIN
        APEX_JSON.parse(in_department);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_manager_id := APEX_JSON.get_varchar2(p_path => 'manager.id');
        APEX_JSON.free_output();

        INSERT INTO departments (
            name,
            manager_id
        ) VALUES (
            v_name,
            v_manager_id
        ) RETURNING ID INTO v_inserted_id;

        v_result := get_department_by_id (
            v_inserted_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END create_department;

----------------------------------- Store --------------------------------------------

    FUNCTION get_store_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
      APEX_JSON.initialize_clob_output();


        FOR s IN (
            SELECT *
            FROM store
            WHERE id = in_id
        ) LOOP
            create_store_response(
                s.id,
                s.name,
                s.location
            );
        END LOOP;

        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_store_by_id;

-------------------------------------------------------------------------------

    FUNCTION get_all_stores (
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
      APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

        FOR s IN (
            SELECT *
            FROM store
        ) LOOP
            create_store_response(
                s.id,
                s.name,
                s.location
            );
        END LOOP;
        APEX_JSON.close_array();
        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_all_stores;

-------------------------------------------------------------------------------

    FUNCTION update_store (
        in_id IN VARCHAR2,
        in_store IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_location VARCHAR2(256);

        v_result INTEGER;
    BEGIN
        APEX_JSON.parse(in_store);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_location := APEX_JSON.get_varchar2(p_path => 'location');
        APEX_JSON.free_output();

        UPDATE store
        SET name = v_name, location = v_location
        WHERE id = in_id;

        v_result := get_store_by_id (
            in_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END update_store;

-------------------------------------------------------------------------------

    FUNCTION create_store (
        in_store IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER
    AS
        v_name VARCHAR2(256);
        v_location VARCHAR2(256);

        v_inserted_id VARCHAR2(32);

        v_result INTEGER;
    BEGIN
        APEX_JSON.parse(in_store);
            v_name := APEX_JSON.get_varchar2(p_path => 'name');
            v_location := APEX_JSON.get_varchar2(p_path => 'location');
        APEX_JSON.free_output();

        INSERT INTO store (
            name,
            location
        ) VALUES (
            v_name,
            v_location
        ) RETURNING ID INTO v_inserted_id;

        v_result := get_store_by_id (
            v_inserted_id,
            response
        );

        COMMIT;
        RETURN RESULT_OK;

     EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END create_store;

----------------------------------- Roles --------------------------------------------

    FUNCTION get_roles (
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
        APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

            FOR r IN (
                SELECT *
                FROM roles
            ) LOOP
                APEX_JSON.open_object();
                    APEX_JSON.write('id',r.id);
                    APEX_JSON.write('name',r.name);
                APEX_JSON.close_object();
            END LOOP;
        APEX_JSON.close_array();
        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_roles;

----------------------------------- Types --------------------------------------------

    FUNCTION get_types (
        response OUT CLOB
    )
    RETURN INTEGER
    AS
    BEGIN
        APEX_JSON.initialize_clob_output();

        APEX_JSON.open_array();

            FOR r IN (
                SELECT *
                FROM types
            ) LOOP
                APEX_JSON.open_object();
                    APEX_JSON.write('id',r.id);
                    APEX_JSON.write('name',r.name);
                APEX_JSON.close_object();
            END LOOP;
        APEX_JSON.close_array();
        response := APEX_JSON.get_clob_output();

        RETURN RESULT_OK;
    EXCEPTION
        WHEN OTHERS THEN
            response := get_error_object(ERROR_500,ERROR_GENERAL_MESSAGE);
            RETURN RESULT_NOK;
    END get_types;

-------------------------------------------------------------------------------

    FUNCTION get_error_object (
        in_code INTEGER,
        in_message VARCHAR2
    )
    RETURN CLOB
    AS
        v_response CLOB;
    BEGIN
        APEX_JSON.initialize_clob_output();

        APEX_JSON.open_object();
            APEX_JSON.write('code',in_code);
            APEX_JSON.write('message',in_message);
        APEX_JSON.close_object;

        v_response := APEX_JSON.get_clob_output;
        RETURN v_response;
    END get_error_object;

-------------------------------------------------------------------------------

    PROCEDURE create_product_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2,
        in_category IN VARCHAR2,
        in_quality IN VARCHAR2,
        in_price IN VARCHAR2,
        in_image IN VARCHAR2,
        in_status IN VARCHAR2,
        in_creation_date IN DATE
    )
    AS
        v_type_id VARCHAR2(32);
        v_type_name VARCHAR2(256);
        v_store_id VARCHAR2(32);
        v_store_name VARCHAR2(256);
        v_store_location  VARCHAR2(256);
    BEGIN
        APEX_JSON.open_object();
            APEX_JSON.write('id',in_id);
            APEX_JSON.write('name',in_name);
            APEX_JSON.write('category',in_category);
            APEX_JSON.write('quality',in_quality);
            APEX_JSON.write('price',in_price);
            APEX_JSON.write('image',in_image);
            APEX_JSON.write('status',in_status);
            APEX_JSON.write('creation_date',to_char(in_creation_date,'DD-MM-YYYY HH24:MI'));
            BEGIN
                SELECT t.id,t.name
                INTO v_type_id,v_type_name
                FROM types t
                JOIN products p ON t.id = p.type_id
                WHERE p.id = in_id;

                APEX_JSON.open_object('type');
                    APEX_JSON.write('id',v_type_id);
                    APEX_JSON.write('name',v_type_name);
                APEX_JSON.close_object();

            EXCEPTION
                WHEN OTHERS THEN
                    v_type_id := null;
                    v_type_name := null;
            END;

            BEGIN
                SELECT s.id,s.name,s.location
                INTO v_store_id,v_store_name,v_store_location
                FROM store s
                JOIN products p ON s.id = p.store_id
                WHERE p.id = in_id;

                APEX_JSON.open_object('store');
                    APEX_JSON.write('id',v_store_id);
                    APEX_JSON.write('name',v_store_name);
                    APEX_JSON.write('location',v_store_location);
                APEX_JSON.close_object();

            EXCEPTION
                WHEN OTHERS THEN
                    v_type_id := null;
                    v_type_name := null;
            END;

            APEX_JSON.open_array('employees');
                FOR e IN (
                    SELECT e.*
                    FROM product_employee pe
                    JOIN employees e ON pe.employee_id = e.id
                    WHERE pe.product_id = in_id
                ) LOOP
                    create_employee_response (
                        e.id,
                        e.name,
                        e.phone_number
                    );
                END LOOP;
            APEX_JSON.close_array();

        APEX_JSON.close_object();
    END create_product_response;

-------------------------------------------------------------------------------

    PROCEDURE create_store_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2,
        in_location IN VARCHAR2
    )
    AS
    BEGIN
        APEX_JSON.open_object();
            APEX_JSON.write('id',in_id);
            APEX_JSON.write('name',in_name);
            APEX_JSON.write('location',in_location);
        APEX_JSON.close_object();
    END create_store_response;

-------------------------------------------------------------------------------

    PROCEDURE create_employee_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2,
        in_phone_number IN VARCHAR2
    )
    AS
    BEGIN
        APEX_JSON.open_object();
            APEX_JSON.write('id',in_id);
            APEX_JSON.write('name',in_name);
            APEX_JSON.write('phone_number',in_phone_number);

            FOR s IN (
                SELECT s.salary
                FROM employees e
                JOIN salary s ON s.department_id = e.department_id AND s.role_id = e.role_id
                WHERE e.id = in_id
            ) LOOP
                APEX_JSON.write('salary',s.salary);
            END LOOP;

            FOR r IN (
                SELECT r.*
                FROM employees e
                JOIN roles r ON e.role_id = r.id
                WHERE e.id = in_id
            ) LOOP
                APEX_JSON.open_object('role');
                    APEX_JSON.write('id',r.id);
                    APEX_JSON.write('name',r.name);
                APEX_JSON.close_object();
            END LOOP;

            FOR d IN (
                SELECT d.*
                FROM employees e
                JOIN departments d ON e.department_id = d.id
                WHERE e.id = in_id
            ) LOOP
                APEX_JSON.open_object('department');
                    APEX_JSON.write('id',d.id);
                    APEX_JSON.write('name',d.name);
                APEX_JSON.close_object();
            END LOOP;

        APEX_JSON.close_object();
    END create_employee_response;

-------------------------------------------------------------------------------

    PROCEDURE create_department_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2
    )
    AS
    BEGIN
        APEX_JSON.open_object();
            APEX_JSON.write('id',in_id);
            APEX_JSON.write('name',in_name);

            FOR e IN (
                SELECT e.*
                FROM employees e
                JOIN departments d ON e.id = d.manager_id
                WHERE d.id = in_id
            ) LOOP
                APEX_JSON.open_object('manager');
                    APEX_JSON.write('id',e.id);
                    APEX_JSON.write('name',e.name);
                APEX_JSON.close_object();
            END LOOP;

        APEX_JSON.close_object();
    END create_department_response;

-------------------------------------------------------------------------------

END ERP_CLOTHING_FACTORY;