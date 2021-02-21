import React,  { useState, useEffect } from "react"
import axios from '../../api/axios'
import { useHistory } from "react-router-dom"
import Select from 'react-select'


const AddProduct = () => {
  let history = useHistory();
  const [product, setProduct] = useState({
    name: "",
    category: "",
    quality: "",
    price: "",
    type: {},
    employees: []
  })

  const { name, category, quality, price, type, employees } = product
  const onInputChange = e => {
    setProduct({ ...product, [e.target.name]: e.target.value })
  }

  const onSubmit = async e => {
    e.preventDefault()
    await axios.post(`/product`, {...product, status: 'active', image: 'imageHere'})
    history.push("/")
  }

  const [ types, setTypes ] = useState([])

  const loadTypes = async () => {
    const result = await axios.get("/types")
    setTypes(result.data.reverse())
  }

  const typeOptions = []
  types.forEach(type => {
    let option = { value:type.id, label:type.name}
    typeOptions.push(option);
  });

  function addTypeSelectedItems(event) {
    let id = event.value
    if(!product.type.id){
      type.id = id
    }
  }

  const [ allEmployees, setAllEmployees ] = useState([])

  useEffect(() => {
    loadTypes()
    loadAllEmployees()
  }, [])

  const loadAllEmployees = async () => {
    const result = await axios.get("/employees")
    setAllEmployees(result.data.reverse())
  }

  const employeeOptions = []
  allEmployees.forEach(employee => {
    let option = { value:employee.id, label:employee.name}
    employeeOptions.push(option);
  });

  function addEmployeeSelectedItems(event) {
    event.forEach(element => {
      let id = element.value
      if(!product.employees.includes({id})){
        product.employees.push({id})
      }
    })
  }

  return (
    <div className="container">
      <div className="w-75 mx-auto shadow p-5">
        <h2 className="text-center mb-4">Add A Product</h2>
        <form onSubmit={e => onSubmit(e)}>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Product Name"
              name="name"
              value={name}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Product Category"
              name="category"
              value={category}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Enter Product Quality"
              name="quality"
              value={quality}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <input
              type="input"
              className="form-control form-control-lg"
              placeholder="Enter Product Price"
              name="price"
              value={price}
              onChange={e => onInputChange(e)}
            />
          </div>
          <div className="form-group">
            <Select
              placeholder="Select Product Type"
              name="type"
              classNamePrefix="type"
              options={typeOptions}
              onChange={e => addTypeSelectedItems(e)}
            />
          </div>
          <div className="form-group">
            <Select
              isMulti
              placeholder="Select Product Manufacturers"
              name="employee"
              classNamePrefix="employee"
              options={employeeOptions}
              onChange={e => addEmployeeSelectedItems(e)}
            />
          </div>
          <button className="btn btn-primary btn-block">Add Product</button>
        </form>
      </div>
    </div>
  );
};

export default AddProduct;