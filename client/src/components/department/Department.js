import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "../../api/axios";

const Department = () => {
  const [department, setDepartment] = useState([]);
  
  const { id } = useParams();
  
  useEffect(() => {
    loadDepartment();
  }, []);

  const loadDepartment = async () => {
    const res = await axios.get(`/department/${id}`);
    setDepartment(res.data);
  };

  return (
    <div className="container py-4">
      <h1 className="display-6">{department.name}</h1>
      <hr />
      <ul className="list-group w-50 ">
        <li className="list-group-item">Name: {department.name}</li>
        <li className="list-group-item">Manager: {department.manager.name}</li>
      </ul>
    </div>
  );
};

export default Department;