import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import filterFactory, { textFilter } from '../../node_modules/react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';


class Model extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            models: [],
            columns: [{
              dataField: 'modelId',
              text: 'Model ID',
              sort: true
            },
            {
              dataField: 'name',
              text: 'Model Name',
              sort: true,
              filter: textFilter()
            }]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.models.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.models.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.models } 
                    columns={ this.state.columns } 
                    filter={ filterFactory() }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default Model;