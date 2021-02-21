import React from "react";
import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.css";
import Employees from "./components/pages/Employees";
import Products from "./components/pages/Products";
import Departments from "./components/pages/Departments";
import Stores from "./components/pages/Stores";

import { withAuthenticator } from "@aws-amplify/ui-react";

import Navbar from "./components/layout/Navbar";
import {
  BrowserRouter as Router,
  Route,
  Switch
} from "react-router-dom";

import NotFound from "./components/pages/NotFound";

import AddEmployee from "./components/employee/AddEmployee";
import Employee from "./components/employee/Employee";
import EditEmployee from "./components/employee/EditEmployee";

import AddDepartment from "./components/department/AddDepartment";
import Department from "./components/department/Department";
import EditDepartment from "./components/department/EditDepartment";

import AddStore from "./components/store/AddStore";
import Store from "./components/store/Store";
import EditStore from "./components/store/EditStore";

import Product from "./components/products/Product";
import AddProduct from "./components/products/AddProduct";
import EditProduct from "./components/products/EditProduct";

function App(props) {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Switch>
          <Route exact path="/" component={Products} />
          <Route exact path="/products" component={Products} />
          <Route exact path="/employees" component={Employees} />
          <Route exact path="/departments" component={Departments} />
          <Route exact path="/stores" component={Stores} />

          <Route exact path="/product/:id" component={Product} />
          <Route exact path="/products/add" component={AddProduct} />
          <Route exact path="/products/edit/:id" component={EditProduct} /> 

          <Route exact path="/employees/add" component={AddEmployee} />
          <Route exact path="/employees/edit/:id" component={EditEmployee} />
          <Route exact path="/employees/:id" component={Employee} />

          <Route exact path="/departments/add" component={AddDepartment} />
          <Route exact path="/departments/edit/:id" component={EditDepartment} />
          <Route exact path="/departments/:id" component={Department} />

          <Route exact path="/stores/add" component={AddStore} />
          <Route exact path="/stores/edit/:id" component={EditStore} />
          <Route exact path="/stores/:id" component={Store} />

          <Route component={NotFound} />
        </Switch>
      </div>
    </Router>
  );
}

export default withAuthenticator(App);