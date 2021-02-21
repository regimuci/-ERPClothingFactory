import React, { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import axios from '../../api/axios'
import { useHistory } from "react-router-dom"

import Loader from '../layout/Loader'

const Products = () => {
  let history = useHistory()
  const [products, setProducts] = useState([])

  useEffect(() => {
    loadProducts()
  }, [])

  const loadProducts = async () => {
    const result = await axios.get("/products")
    setProducts(result.data.reverse())
  }

  const deleteProduct = async id => {
    await axios.put(`/product/${id}`, {status: 'deleted'})
    history.push("/")
  }

  if (products.length === 0) {
    return (
      <Loader/>
    )
  }

  return (
    <div className="container">
      <div className="py-4">
        <h1>Products</h1>
        <table className="table border shadow">
          <thead className="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Image</th>
              <th scope="col">Name</th>
              <th scope="col">Type</th>
              <th scope="col">Quality</th>
              <th scope="col">Price</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product, index) => (
              <tr>
                <th scope="row">{index + 1}</th>
                <td>
                  <img src="tshirt.png" style={{width:30 +'px', height:30 +'px'}} ></img>
                </td>
                <td>
                  <Link to={`/product/${product.id}`}>
                    {product.name}  
                  </Link>
                </td>
                <td>{product.type.name}</td>
                <td>{product.quality}</td>
                <td>{product.price} ALL</td>
                <td>
                  <Link
                    className="btn btn-warning mr-2"
                    to={`/products/edit/${product.id}`}
                  >
                    Update
                  </Link>
                  <Link
                    className="btn btn-danger"
                    onClick={() => deleteProduct(product.id)}
                  >
                    Delete
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="row justify-content-end">
          <div className="col-4">
            <Link className="btn btn-success" to="/products/add">Add Product</Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Products;