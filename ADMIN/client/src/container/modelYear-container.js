import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import ModelYear from "../component/modelYear";

class ModelYearCon extends React.Component{
    render(){
        return(
            <ModelYear handleClick={this.props.loadModelYears} modelYears={this.props.modelYears}></ModelYear>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(ModelYearCon);