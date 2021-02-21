import React, { useState, useEffect } from "react"
// import axios from "axios"
import { Link } from "react-router-dom"
import axios from '../../api/axios'

import Loader from '../layout/Loader'

const Stores = () => {
  const [stores, setStores] = useState([])

  useEffect(() => {
    loadStores()
  }, [])

  const loadStores = async () => {
    const result = await axios.get("/stores")
    setStores(result.data.reverse())
  }

  const deleteStore = async id => {
    await axios.delete(`/stores/${id}`)
    loadStores()
  }

  if (stores.length === 0) {
    return (
      <Loader/>
    )
  }

  return (
    <div className="container">
      <div className="py-4">
        <h1>Stores</h1>
        <table className="table border shadow">
          <thead className="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">Location</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {stores.map((store, index) => (
              <tr>
                <th scope="row">{index + 1}</th>
                <td>
                  <Link to={`/stores/${store.id}`}>
                    {store.name}
                  </Link>
                </td>
                <td>
                  {store.location}
                </td>
                <td>
                  <Link className="btn btn-warning mr-2" to={`/stores/edit/${store.id}`}>Update</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="row justify-content-end">
          <div className="col-4">
            <Link className="btn btn-success" to="/stores/add">Add Store</Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Stores;