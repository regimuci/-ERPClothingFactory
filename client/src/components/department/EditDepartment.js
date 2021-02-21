import React,  { useState, useEffect } from "react"
import { Link, useParams } from "react-router-dom"
import axios from '../../api/axios'
import Select from 'react-select'
import { useHistory } from "react-router-dom"

const EditDepartment = () => {
  let history = useHistory();
  const { id } = useParams();

  const [department, setDepartment] = useState({
    name: "",
    manager: {}
  });

  const onInputChange = e => {
    setDepartment({ ...department, [e.target.name]: e.target.value });
  };

  const { name, manager } = department;

  const onSubmit = async e => {
    e.preventDefault();
    await axios.put(`/department/${id}`, department);
    history.push("/departments");
  };

  const [ employees, setEmployees ] = useState([])

  useEffect(() => {
    loadEmployees()
    loadDepartment()
  }, [])

  const loadEmployees = async () => {
    const result = await axios.get("/employees")
    setEmployees(result.data.reverse())
  }

  const loadDepartment = async () => {
    const result = await axios.get(`/department/${id}`)
    setDepartment(result.data)
  }

  const managerOptions = []
  employees.forEach(employee => {
    let option = { value:employee.id, label:employee.name}
    managerOptions.push(option);
  });

  function addManagerSelectedItems(event) {
    let id = event.value;
    if(!department.manager.id){
      manager.id = id;
    }
  }

  return (
    <div className="container">
      <div className="w-75 mx-auto shadow p-5">
        <h2 className="text-center mb-4">Update Department</h2>
        <form onSubmit={e => onSubmit(e)}>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Department Name"
              name="name"
              value={name}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <Select
              placeholder="Select Department Manager"
              name="department"
              classNamePrefix="department"
              options={managerOptions}
              onChange={e => addManagerSelectedItems(e)}
            />
          </div>
          <button className="btn btn-warning btn-block">Update</button>
        </form>
      </div>
    </div>
  );
};

export default EditDepartment;