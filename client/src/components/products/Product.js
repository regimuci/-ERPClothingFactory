import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "../../api/axios";

import Loader from '../layout/Loader'

const Product = () => {
  const [product, setProduct] = useState([]);
  
  const { id } = useParams();
  
  useEffect(() => {
    loadProduct();
  }, []);

  const loadProduct = async () => {
    const res = await axios.get(`/product/${id}`);
    setProduct(res.data);
  };

  if (product.length === 0) {
    return (
      <Loader/>
    )
  }

  return (
    <div className="container py-4">
      <h1 className="display-6">{product.name}</h1>
      <hr/>
      <ul className="list-group w-50">
        <li className="list-group-item">Name: {product.name}</li>
        <li className="list-group-item">Category: {product.category}</li>
        <li className="list-group-item">Quality: {product.quality}</li>
        <li className="list-group-item">Price: {product.price} ALL</li>
        <li className="list-group-item">Type: {product.type.name}</li>
      </ul>
    </div>
  );
};

export default Product;