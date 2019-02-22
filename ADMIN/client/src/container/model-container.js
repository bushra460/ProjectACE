import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import Model from "../component/model";

class ModelCon extends React.Component{
    render(){
        return(
            <Model handleClick={this.props.loadModels} models={this.props.models}></Model>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(ModelCon);