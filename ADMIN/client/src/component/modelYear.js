import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';


class ModelYear extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modelYears: [],
            columns: [{
              dataField: 'modelYearId',
              text: 'Model Year ID',
              sort: true
            },
            {
              dataField: 'yearValue',
              text: 'Year',
              sort: true
            }]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.modelYears.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.modelYears.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.modelYears } 
                    columns={ this.state.columns }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default ModelYear;