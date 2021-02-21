import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "../../api/axios";

import Loader from '../layout/Loader'

const Employee = () => {
  const [employee, setEmployee] = useState([]);
  
  const { id } = useParams();
  
  useEffect(() => {
    loadEmployee();
  }, []);

  const loadEmployee = async () => {
    const res = await axios.get(`/employee/${id}`);
    setEmployee(res.data);
  };

  if (employee.length === 0) {
    return (
      <Loader/>
    )
  }

  return (
    <div className="container py-4">
      <h1 className="display-6">{employee.name}</h1>
      <hr />
      <ul className="list-group w-50 ">
        <li className="list-group-item">Name: {employee.name}</li>
        <li className="list-group-item">Phone: {employee.phone_number}</li>
        <li className="list-group-item">Department: {employee.department.name}</li>
        <li className="list-group-item">Role: {employee.role.name}</li>
        <li className="list-group-item">Salary: {employee.salary} ALL</li>
      </ul>
    </div>
  );
};

export default Employee;