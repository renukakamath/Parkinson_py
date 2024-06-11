from flask import Blueprint,render_template,request,redirect,url_for,session,flash
from database import*

pharmacy=Blueprint("pharmacy",__name__)

@pharmacy.route("/pharmacy_home") 
def pharmacy_home():
	if not session.get("lid") is None:
		return render_template("pharmacy_home.html")
	else:
		return redirect(url_for("public.login"))

@pharmacy.route("/pharmacy_add_medicine_details",methods=['get','post'])
def pharmacy_add_medicine_details():
	if not session.get("lid") is None:
		data={}
		q="select * from medicines where pharmacy_id='%s'"%(session['pid'])
		print(q)
		data['viewr']=select(q)
		if 'id' in request.args:
			id=request.args['id']
			q="delete from medicines where medicine_id='%s'"%(id)
			delete(q)
			flash("Removed")
			return redirect(url_for("pharmacy.pharmacy_add_medicine_details"))

		if 'submit' in request.form:
			m_name=request.form['m_name']
			desc=request.form['desc']
			e_date=request.form['e_date']
			rt=request.form['rt']
			available_qty=request.form['available_qty']
			q="INSERT INTO medicines(`pharmacy_id`,`medicine_name`,`description`,`expiry_date`,`rate`,available_qty)VALUES('%s','%s','%s','%s','%s','%s')"%(session['pid'],m_name,desc,e_date,rt,available_qty)
			insert(q)
			flash("Medicine Added")
			return redirect(url_for("pharmacy.pharmacy_add_medicine_details"))
		return render_template("pharmacy_add_medicine_details.html",data=data)
	else:
		return redirect(url_for("public.login"))







@pharmacy.route("/pharmacy_view_and_manage_user_medicine_prescriptions",methods=['get','post'])
def pharmacy_view_and_manage_user_medicine_prescriptions():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(`doctors`.`first_name`,' ',`doctors`.`last_name`) AS dname,`doctors`.`phone` AS dphone,CONCAT(`patients`.`first_name`,' ',`patients`.`last_name`) AS pname,`patients`.`phone` AS pphone,`medicine_prescriptions`.`date_time` AS pre_date,`medicine_prescriptions`.`status` AS md_status FROM `medicine_prescriptions` INNER JOIN `medicines` USING(`medicine_id`) INNER JOIN `bookings` USING(`book_id`) INNER JOIN `patients` USING(`patient_id`) INNER JOIN `schedule` USING(`schedule_id`) INNER JOIN `doctors` USING(`doctor_id`) WHERE `pharmacy_id`='%s' AND (`medicine_prescriptions`.`status`='Forward' OR `medicine_prescriptions`.`status`='Accept') AND `medicine_prescriptions`.`status`='Forward' GROUP BY pname,pre_date"%(session['pid'])
		data['view_pre']=select(q)
		# q="select * from medicines"
		# data['view']=select(q)
		return render_template("pharmacy_view_and_manage_user_medicine_prescriptions.html",data=data)
	else:
		return redirect(url_for("public.login"))



@pharmacy.route("/pharmacy_add_amount",methods=['get','post'])
def pharmacy_add_amount():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		data['book_id']=book_id

		q="SELECT * FROM `payments` WHERE `book_id`='%s' AND `payment_type`='Medicne'"%(book_id)
		res=select(q)
		data['book_ids']=res
  
		if 'md_amount' in request.form: 
			amount=request.form['amount']
			q="INSERT INTO `payments` VALUES(NULL,'%s','%s',curdate(),'Medicne')"%(book_id,amount)
			insert(q)
			q="UPDATE `medicine_prescriptions` SET `status`='Accept' WHERE `book_id`='%s'"%(book_id)
			update(q)
			return redirect(url_for("pharmacy.pharmacy_view_and_manage_user_medicine_prescriptions"))
		
		return render_template("pharmacy_add_amount.html",data=data)
	else:
		return redirect(url_for("public.login"))
