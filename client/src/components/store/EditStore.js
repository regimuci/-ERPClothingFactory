import React,  { useState, useEffect } from "react"
import { Link, useParams } from "react-router-dom"
import axios from '../../api/axios'
import { useHistory } from "react-router-dom"

const EditStore = () => {
  let history = useHistory()
  const { id } = useParams()

  const [store, setStore] = useState({
    name: "",
    location: ""
  })

  const onInputChange = e => {
    setStore({ ...store, [e.target.name]: e.target.value })
  }

  const { name, location } = store;

  const onSubmit = async e => {
    e.preventDefault()
    await axios.put(`/store/${id}`, store)
    history.push("/stores")
  }

  useEffect(() => {
    loadStore()
  }, [])

  const loadStore = async () => {
    const result = await axios.get(`/store/${id}`)
    setStore(result.data)
  }

  return (
    <div className="container">
      <div className="w-75 mx-auto shadow p-5">
        <h2 className="text-center mb-4">Update Store</h2>
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
          <button className="btn btn-warning btn-block">Update</button>
        </form>
      </div>
    </div>
  )
}

export default EditStore