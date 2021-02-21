import React, { useState, useEffect } from "react"
// import axios from "axios"
import { Link } from "react-router-dom"
import axios from '../../api/axios'

import Loader from '../layout/Loader'

const Employees = () => {
  const [employees, setEmployees] = useState([])

  useEffect(() => {
    loadEmployees()
  }, [])

  const loadEmployees = async () => {
    const result = await axios.get("/employees")
    setEmployees(result.data.reverse())
  }
  
  if (employees.length === 0) {
    return (
      <Loader/>
    )
  }

  return (
    <div className="container">
      <div className="py-4">
        <h1>Employees</h1>
        <table className="table border shadow">
          <thead className="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">Phone</th>
              <th scope="col">Department</th>
              <th scope="col">Role</th>
              <th scope="col">Salary</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((employee, index) => (
              <tr>
                <th scope="row">{index + 1}</th>
                <td>
                  <Link to={`/employees/${employee.id}`}>
                    {employee.name}
                  </Link>
                </td>
                <td>{employee.phone_number}</td>
                <td>{employee.department.name}</td>
                <td>{employee.role.name}</td>
                <td>{employee.salary} ALL</td>
                <td>
                  <Link className="btn btn-warning mr-2" to={`/employees/edit/${employee.id}`}>Update</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="row justify-content-end">
          <div className="col-4">
            <Link className="btn btn-success" to="/employees/add">Add Employee</Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Employees;