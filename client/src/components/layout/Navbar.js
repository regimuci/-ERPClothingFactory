import React from "react";
import { Link, NavLink } from "react-router-dom";

import { AmplifySignOut } from "@aws-amplify/ui-react";

import "./Navbar.css"

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <Link className="navbar-brand" to={'/'}>
          <img src="favicon.ico" style={{width:30 +'px', height:30 +'px'}}/>  Home
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <NavLink className="nav-link" exact to="/products">
                Products
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" exact to="/departments">
                Departments
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" exact to="/employees">
                Employees
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" exact to="/stores">
                Stores
              </NavLink>
            </li>
          </ul>
          <AmplifySignOut className="sign-out"/>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;