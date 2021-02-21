import React,  { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import axios from '../../api/axios'
import Select from 'react-select';
import { useHistory } from "react-router-dom";

const EditEmployee = () => {
  let history = useHistory();
  const { id } = useParams();

  const [employee, setEmployee] = useState({
    name: "",
    phone_number: "",
    role: {},  
    department: {}
  });

  const onInputChange = e => {
    setEmployee({ ...employee, [e.target.name]: e.target.value });
  };

  const { name, phone_number, role, department } = employee;

  const onSubmit = async e => {
    e.preventDefault();
    console.log(JSON.stringify(employee));
    await axios.put(`/employee/${id}`, employee);
    history.push("/employees");
  };

  const [ roles, setRoles ] = useState([])
  const [ departments, setDepartments ] = useState([])

  useEffect(() => {
    loadRoles()
    loadDepartments()
    loadEmployee()
  }, [])

  const loadRoles = async () => {
    const result = await axios.get("/roles")
    setRoles(result.data.reverse())
  }

  const loadDepartments = async () => {
    const result = await axios.get("/departments")
    setDepartments(result.data.reverse())
  }

  const loadEmployee = async () => {
    const result = await axios.get(`/employee/${id}`)
    setEmployee(result.data)
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
        <h2 className="text-center mb-4">Update Employee</h2>
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
              classNamePrefix="department"
              options={departmentOptions}
              onChange={e => addDepartmentSelectedItems(e)}
            />
          </div>
          <button className="btn btn-warning btn-block">Update</button>
        </form>
      </div>
    </div>
  );
};

export default EditEmployee;