import React,  { useState, useEffect } from "react";
import axios from '../../api/axios'
import Select from 'react-select';
import { useHistory } from "react-router-dom";

const AddEmployee = () => {
  let history = useHistory();
  const [employee, setEmployee] = useState({
    name: "",
    phone_number: "",
    role: {},  
    department: {}
  });

  const { name, phone_number, role, department } = employee;

  const onInputChange = e => {
    setEmployee({ ...employee, [e.target.name]: e.target.value });
  };

  const onSubmit = async e => {
    e.preventDefault();
    await axios.post("/employee", employee);
    history.push("/employees");
  };

  const [ roles, setRoles ] = useState([])
  const [ departments, setDepartments ] = useState([])

  useEffect(() => {
    loadRoles()
    loadDepartments()
  }, [])

  const loadRoles = async () => {
    const result = await axios.get("/roles")
    setRoles(result.data.reverse())
  }

  const loadDepartments = async () => {
    const result = await axios.get("/departments")
    setDepartments(result.data.reverse())
  }

  const departmentOptions = []
  departments.forEach(department => {
    let option = { value:department.id, label:department.name}
    departmentOptions.push(option);
  });

  const roleOptions = []
  roles.forEach(role => {
    let option = { value:role.id, label:role.name}
    roleOptions.push(option);
  });

  function addRoleSelectedItems(event) {
    let id = event.value;
    if(!employee.role.id){
      role.id = id;
    }
  }

  function addDepartmentSelectedItems(event) {
    let id = event.value;
    if(!employee.department.id){
      department.id = id;
    }
  }

  return (
    <div className="container">
      <div className="w-75 mx-auto shadow p-5">
        <h2 className="text-center mb-4">Add An Emplotee</h2>
        <form onSubmit={e => onSubmit(e)}>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Employee Name"
              name="name"
              value={name}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Employee Phone"
              name="phone_number"
              value={phone_number}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <Select
              placeholder="Select Employee Role"
              name="role"
              classNamePrefix="role"
              options={roleOptions}
              onChange={e => addRoleSelectedItems(e)}
            />
          </div>
          <div className="form-group">
            <Select
              placeholder="Select Employee Department"
              name="department"
              classNamePrefix="Department"
              options={departmentOptions}
              onChange={e => addDepartmentSelectedItems(e)}
            />
          </div>
          <button className="btn btn-primary btn-block">Add Employee</button>
        </form>
      </div>
    </div>
  );
};

export default AddEmployee;