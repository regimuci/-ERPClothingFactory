import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "../../api/axios";

const Store = () => {
  const [store, setStore] = useState([])
  
  const { id } = useParams()

  const loadStore = async () => {
    const res = await axios.get(`/store/${id}`)
    setStore(res.data)
  }

  const [products, setProducts] = useState([]);

  const loadSoldProducts = async () => {
    const res = await axios.get(`/products/${id}/sold`)
    setProducts(res.data)
  };

  useEffect(() => {
    loadStore()
    loadSoldProducts()
  }, [])

  return (
    <div className="container">
      <h1 className="display-6">{store.name}</h1>
      <hr/>
      <div className="row">
        <div className="col-sm-4">
          <ul className=" list-group">
            <li className="list-group-item">Name: {store.name}</li>
            <li className="list-group-item">Location: {store.location}</li>
            <li className="list-group-item">Products sold: {products.length}</li>
          </ul>
        </div>
        <div className="col-sm-8">
          <table className="table border shadow">
            <thead className="thead-dark">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Image</th>
                <th scope="col">Name</th>
                <th scope="col">Type</th>
                <th scope="col">Quality</th>
                <th scope="col">Price</th>
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
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}

export default Store;