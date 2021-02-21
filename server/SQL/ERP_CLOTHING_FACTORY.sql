create or replace NONEDITIONABLE PACKAGE ERP_CLOTHING_FACTORY AS

    -- Variables
    RESULT_OK INTEGER := 0;
    RESULT_NOK INTEGER := 1;

    -- Status
    STATUS_ACTIVE VARCHAR2(64) := 'active';
    STATUS_SOLD VARCHAR2(64) := 'sold';
    STATUS_DELETED VARCHAR2(64) := 'deleted';

    FAIL VARCHAR2(64) := 'Fail';
    ERROR_500 INTEGER := 500;
    ERROR_400 INTEGER := 400;

    ERROR_GENERAL_MESSAGE VARCHAR2(64) := 'Something went wrong';
    ERROR_PRODUCT_NOT_FOUND VARCHAR2(64) := 'Product not found';
    ERROR_EMPLOYEE_NOT_FOUND VARCHAR2(64) := 'Employee not found';
    ERROR_STATUS_SOLD VARCHAR2(64) := 'Status is sold and cannot be changed';
    ERROR_STORE_NULL VARCHAR2(64) := 'Store cannot be empty when trying to sell a product';


----------------------------------- Products --------------------------------------------

    FUNCTION get_product_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_all_products (
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION update_product (
        in_id IN VARCHAR2,
        in_product IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION create_product (
        in_product IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_products_by_employee(
        in_employee_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_sold_products(
        in_store_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER;

----------------------------------- Employees --------------------------------------------

    FUNCTION get_employee_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_all_employees (
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION update_employee (
        in_id IN VARCHAR2,
        in_employee IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION create_employee (
        in_employee IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

----------------------------------- Department --------------------------------------------

    FUNCTION get_department_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_all_departments (
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION update_department (
        in_id IN VARCHAR2,
        in_department IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION create_department (
        in_department IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

----------------------------------- Store --------------------------------------------

    FUNCTION get_store_by_id (
        in_id VARCHAR2,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_all_stores (
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION update_store (
        in_id IN VARCHAR2,
        in_store IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION create_store (
        in_store IN CLOB,
        response OUT CLOB
    )
    RETURN INTEGER;

----------------------------------- Roles --------------------------------------------

    FUNCTION get_roles (
        response OUT CLOB
    )
    RETURN INTEGER;

----------------------------------- Types --------------------------------------------

    FUNCTION get_types (
        response OUT CLOB
    )
    RETURN INTEGER;

-------------------------------------------------------------------------------

    FUNCTION get_error_object (
        in_code INTEGER,
        in_message VARCHAR2
    )
    RETURN CLOB;

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
    );

-------------------------------------------------------------------------------

    PROCEDURE create_store_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2,
        in_location IN VARCHAR2
    );

-------------------------------------------------------------------------------

    PROCEDURE create_employee_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2,
        in_phone_number IN VARCHAR2
    );

-------------------------------------------------------------------------------

    PROCEDURE create_department_response (
        in_id IN VARCHAR2,
        in_name IN VARCHAR2
    );

-------------------------------------------------------------------------------

END ERP_CLOTHING_FACTORY;