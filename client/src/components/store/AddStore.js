import React,  { useState, useEffect } from "react";
import axios from '../../api/axios'
import Select from 'react-select';
import { useHistory } from "react-router-dom";

const AddStore = () => {
  let history = useHistory();
  const [store, setStore] = useState({
    name: "",
    location: ""
  });

  const { name, location } = store;

  const onInputChange = e => {
    setStore({ ...store, [e.target.name]: e.target.value });
  };

  const onSubmit = async e => {
    e.preventDefault();
    await axios.post("/store", store);
    history.push("/stores");
  };

  return (
    <div className="container">
      <div className="w-75 mx-auto shadow p-5">
        <h2 className="text-center mb-4">Add A Store</h2>
        <form onSubmit={e => onSubmit(e)}>
        <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Store Name"
              name="name"
              value={name}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Store Location"
              name="location"
              value={location}
              onChange={e => onInputChange(e)}
            />
          </div>
          <button className="btn btn-primary btn-block">Add Store</button>
        </form>
      </div>
    </div>
  );
};

export default AddStore;