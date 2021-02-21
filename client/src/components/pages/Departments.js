import React, { useState, useEffect } from "react"
// import axios from "axios"
import { Link } from "react-router-dom"
import axios from '../../api/axios'

import Loader from '../layout/Loader'

const Departments = () => {
  const [departments, setDepartments] = useState([])

  useEffect(() => {
    loadDepartments()
  }, [])

  const loadDepartments = async () => {
    const result = await axios.get("/departments")
    setDepartments(result.data.reverse())
  }

  const deleteEmployee = async id => {
    await axios.delete(`/departments/${id}`)
    loadDepartments()
  }

  if (departments.length === 0) {
    return (
      <Loader/>
    )
  }

  return (
    <div className="container">
      <div className="py-4">
        <h1>Departments</h1>
        <table className="table border shadow">
          <thead className="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">Manager</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {departments.map((department, index) => (
              <tr>
                <th scope="row">{index + 1}</th>
                <td>
                    {department.name}
                </td>
                <td>
                  <Link to={`/employees/${department.manager.id}`}>
                    {department.manager.name}
                  </Link>
                </td>
                <td>
                  <Link className="btn btn-warning mr-2" to={`/departments/edit/${department.id}`}>Update</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="row justify-content-end">
          <div className="col-4">
            <Link className="btn btn-success" to="/departments/add">Add Department</Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Departments;