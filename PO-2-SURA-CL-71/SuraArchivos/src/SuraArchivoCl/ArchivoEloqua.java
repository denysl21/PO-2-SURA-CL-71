package SuraArchivoCl;

public class ArchivoEloqua {

	/*
	 * #PERIODO 5 #RUT 1 AP_MATERNO 1 TODAS AP_PATERNO 1 TODAS CELULAR 1
	 * COD_AGENCIA_SOLICITUD 5 CODIGO_AREA_CELULAR 1 CODIGO_AREA_TELEFONO 1
	 * DESCRIPCION_MODALIDAD 4 EDAD 1 TODAS EMAIL 1 ENTIDAD 4 ESTADO_TRAMITE 5
	 * FEC_EMISION 2 FEC_NACIMIENTO 1 TODAS FEC_SELECCION_MODALIDAD 4
	 * FEC_SOLICITUD_TRAMITE 1 FECHA_PAGO 5 FORMA_DE_PAGO 5
	 * IND_SOLICITA_PAGO_PRELIMINAR 5 NOMBRE 1 NUM_TRAMITE 5 NUM_TRAMITE_PENSION 1
	 * PASO 1 TODAS RUT 5 SEXO 1 TODAS TELEFONO 1 TIPO_MODALIDAD 5 TIPO_SELECCION 5
	 * TIPO_TRAMITE 5 TIPO_TRAMITE_PENSION 1 TODAS HIST_SMS_ETP_1 HIST_SMS_ETP_2
	 * HIST_SMS_ETP_4 HIST_SMS_ETP_5 SMS_ETAPA SMS_STATUS SMS_CODIGO
	 */

	private String sms_codigo;
	private String sms_status;

	public String getSms_codigo() {
		return sms_codigo;
	}

	public void setSms_codigo(String sms_codigo) {
		this.sms_codigo = sms_codigo;
	}

	public String getSms_status() {
		return sms_status;
	}

	public void setSms_status(String sms_status) {
		this.sms_status = sms_status;
	}

	private String periodo;
	private String rut;
	private String ap_materno;
	private String ap_paterno;
	private String celular;

	private String cod_agencia_solicitud;
	private String codigo_area_celular;
	private String codigo_area_telefono;
	private String descripcion_modalidad;
	private String edad;

	private String emali;
	private String entidad;
	private String estado_tramite;
	private String fec_emision;
	private String fec_nacimiento;
	private String fec_seleccion_modalidad;
	private String fecha_de_pago;
	private int sms_etapa;

	public int getSms_etapa() {
		return sms_etapa;
	}

	public void setSms_etapa(int tipoArchivo) {
		this.sms_etapa = tipoArchivo;
	}

	public String getFecha_de_pago() {
		return fecha_de_pago;
	}

	public void setFecha_de_pago(String fecha_de_pago) {
		this.fecha_de_pago = fecha_de_pago;
	}

	private String fec_solicitud_tramite;
	private String forma_de_pago;
	private String ind_solicita_pago_preliminar;
	private String nombre;
	private String num_tramite;

	private String num_tramite_pension;
	private String paso;
	private String rutsin;
	private String sexo;
	private String telefono;

	private String tipo_modalidad;
	private String tipo_seleccion;
	private String tipo_tramite;
	private String tipo_tramite_pension;

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periado) {
		this.periodo = periado;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getAp_materno() {
		return ap_materno;
	}

	public void setAp_materno(String ap_materno) {
		this.ap_materno = ap_materno;
	}

	public String getAp_paterno() {
		return ap_paterno;
	}

	public void setAp_paterno(String ap_paterno) {
		this.ap_paterno = ap_paterno;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCod_agencia_solicitud() {
		return cod_agencia_solicitud;
	}

	public void setCod_agencia_solicitud(String cod_agencia_solicitud) {
		this.cod_agencia_solicitud = cod_agencia_solicitud;
	}

	public String getCodigo_area_celular() {
		return codigo_area_celular;
	}

	public void setCodigo_area_celular(String codigo_area_celular) {
		this.codigo_area_celular = codigo_area_celular;
	}

	public String getCodigo_area_telefono() {
		return codigo_area_telefono;
	}

	public void setCodigo_area_telefono(String codigo_area_telefono) {
		this.codigo_area_telefono = codigo_area_telefono;
	}

	public String getDescripcion_modalidad() {
		return descripcion_modalidad;
	}

	public void setDescripcion_modalidad(String descripcion_modalidad) {
		this.descripcion_modalidad = descripcion_modalidad;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getEmali() {
		return emali;
	}

	public void setEmali(String emali) {
		this.emali = emali;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getEstado_tramite() {
		return estado_tramite;
	}

	public void setEstado_tramite(String estado_tramite) {
		this.estado_tramite = estado_tramite;
	}

	public String getFec_emision() {
		return fec_emision;
	}

	public void setFec_emision(String fec_emision) {
		this.fec_emision = fec_emision;
	}

	public String getFec_nacimiento() {
		return fec_nacimiento;
	}

	public void setFec_nacimiento(String fec_nacimiento) {
		this.fec_nacimiento = fec_nacimiento;
	}

	public String getFec_seleccion_modalidad() {
		return fec_seleccion_modalidad;
	}

	public void setFec_seleccion_modalidad(String fec_seleccion_modalidad) {
		this.fec_seleccion_modalidad = fec_seleccion_modalidad;
	}

	public String getFec_solicitud_tramite() {
		return fec_solicitud_tramite;
	}

	public void setFec_solicitud_tramite(String fec_solicitud_tramite) {
		this.fec_solicitud_tramite = fec_solicitud_tramite;
	}

	public String getForma_de_pago() {
		return forma_de_pago;
	}

	public void setForma_de_pago(String forma_de_pago) {
		this.forma_de_pago = forma_de_pago;
	}

	public String getInd_solicita_pago_preliminar() {
		return ind_solicita_pago_preliminar;
	}

	public void setInd_solicita_pago_preliminar(String ind_solicita_pago_preliminar) {
		this.ind_solicita_pago_preliminar = ind_solicita_pago_preliminar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNum_tramite() {
		return num_tramite;
	}

	public void setNum_tramite(String num_tramite) {
		this.num_tramite = num_tramite;
	}

	public String getNum_tramite_pension() {
		return num_tramite_pension;
	}

	public void setNum_tramite_pension(String num_tramite_pension) {
		this.num_tramite_pension = num_tramite_pension;
	}

	public String getPaso() {
		return paso;
	}

	public void setPaso(String paso) {
		this.paso = paso;
	}

	public String getRutsin() {
		return rutsin;
	}

	public void setRutsin(String rutsin) {
		this.rutsin = rutsin;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipo_modalidad() {
		return tipo_modalidad;
	}

	public void setTipo_modalidad(String tipo_modalidad) {
		this.tipo_modalidad = tipo_modalidad;
	}

	public String getTipo_seleccion() {
		return tipo_seleccion;
	}

	public void setTipo_seleccion(String tipo_seleccion) {
		this.tipo_seleccion = tipo_seleccion;
	}

	public String getTipo_tramite() {
		return tipo_tramite;
	}

	public void setTipo_tramite(String tipo_tramite) {
		this.tipo_tramite = tipo_tramite;
	}

	public String getTipo_tramite_pension() {
		return tipo_tramite_pension;
	}

	public void setTipo_tramite_pension(String tipo_tramite_pension) {
		this.tipo_tramite_pension = tipo_tramite_pension;
	}

	public String getHist_sms_etp_1() {
		return hist_sms_etp_1;
	}

	public void setHist_sms_etp_1(String hist_sms_etp_1) {
		this.hist_sms_etp_1 = hist_sms_etp_1;
	}

	public String getHist_sms_etp_2() {
		return hist_sms_etp_2;
	}

	public void setHist_sms_etp_2(String hist_sms_etp_2) {
		this.hist_sms_etp_2 = hist_sms_etp_2;
	}

	public String getHist_sms_etp_4() {
		return hist_sms_etp_4;
	}

	public void setHist_sms_etp_4(String hist_sms_etp_4) {
		this.hist_sms_etp_4 = hist_sms_etp_4;
	}

	public String getHist_sms_etp_5() {
		return hist_sms_etp_5;
	}

	public void setHist_sms_etp_5(String hist_sms_etp_5) {
		this.hist_sms_etp_5 = hist_sms_etp_5;
	}

	private String hist_sms_etp_1;
	private String hist_sms_etp_2;
	private String hist_sms_etp_4;
	private String hist_sms_etp_5;

}
