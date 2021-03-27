import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './npa-report.reducer';
import { INpaReport } from 'app/shared/model/npa-report.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INpaReportDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NpaReportDetail = (props: INpaReportDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { npaReportEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="myApp.npaReport.detail.title">NpaReport</Translate> [<b>{npaReportEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="npaId">
              <Translate contentKey="myApp.npaReport.npaId">Npa Id</Translate>
            </span>
          </dt>
          <dd>{npaReportEntity.npaId}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="myApp.npaReport.location">Location</Translate>
            </span>
          </dt>
          <dd>{npaReportEntity.location}</dd>
        </dl>
        <Button tag={Link} to="/npa-report" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/npa-report/${npaReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ npaReport }: IRootState) => ({
  npaReportEntity: npaReport.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NpaReportDetail);
