import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import Manufacturer from "../component/manufacturer";

class ManufacturerCon extends React.Component{
    render(){
        return(
            <Manufacturer handleClick={this.props.loadManufacturers} manufacturers={this.props.manufacturers}></Manufacturer>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(ManufacturerCon);