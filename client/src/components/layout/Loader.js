import React from "react"
import Loader from 'react-loader-spinner'

import '../layout/Loader.css'

export default class App extends React.Component {
 //other logic
   render() {
    return(
      <div id="loader">
         <Loader
            type="Circles"
            color="#00BFFF"
            height={100}
            width={100}
         />
     </div>
    )
   }
}