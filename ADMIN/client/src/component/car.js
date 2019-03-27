import React from "react";
import ReactTable from '../../node_modules/react-table';
import matchSorter from '../../node_modules/match-sorter';

class Car extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cars: [],
            columns:[
            {
              Header: "Cars",
              columns: [
                {
                  Header: 'Id',
                  accessor: 'carId'
                },
                {
                  Header: 'Make',
                  id: "mnfctrNm",
                  accessor: 'manufacturer.name',
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["mnfctrNm"] }),
                  filterAll: true
                },
                {
                  Header: 'Model',
                  id: "mdlNm",
                  accessor: 'model.name',
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["mdlNm"] }),
                  filterAll: true
                },
                {
                  Header: 'Year',
                  id: "yr",
                  accessor: 'modelYear.yearValue',
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["yr"] }),
                  filterAll: true
                },
                {
                  Header: "Active",
                  id: "actv",
                  // maxWidth: 80,
                  accessor: d => d.active.toString(),
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["actv"] }),
                  filterAll: true
                }
              ]
            }
          ]
        }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.cars.length)
        return (
            <div className="container" style={{ marginTop: 15 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.cars.length > 0 ? 
                <ReactTable
                data={this.props.cars}
                filterable
                defaultFilterMethod={(filter, row) =>
                String(row[filter.id]) === filter.value}
                columns={this.state.columns}
                defaultPageSize = {5}
                pageSizeOptions = {[5, 10, 15, 20]}
                className="-striped -highlight"
                /> : null}
            </div>
        );
      }
}

export default Car;