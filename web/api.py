from flask import *
from database import *
from predict import *
 
import demjson
import uuid 

import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	
	username = request.args['username']
	password = request.args['password']
	q="SELECT * from login where username='%s' and password='%s'" % (username,password)
	res = select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
		data['method']='login'
	else:
		data['status']	= 'failed'
		data['method']='login'
	return  demjson.encode(data)


@api.route('/userregister')
def userregister():

	data = {}

	fname=request.args['fname']
	lname=request.args['lname']
	phone=request.args['phone']
	email=request.args['email']
	place=request.args['place']
	username=request.args['username']
	password=request.args['password']

	q1="SELECT * FROM login WHERE username='%s'" %(username)
	print(q1)
	r=select(q1)
	print(r)
	if r:
		data['status']='duplicate'
	else:
		q= "INSERT INTO `login` VALUES(NULL,'%s','%s','user')"%(username,password)
		lid = insert(q)
		qr="INSERT INTO `user` VALUES(NULL,'%s','%s','%s','%s','%s','%s')"%(lid,fname,lname,place,phone,email)		
		print(qr)
		id=insert(qr)

		if id>0:
			data['status'] = 'success'
		else:
			data['status'] = 'failed'
	return demjson.encode(data)





@api.route('/user_manage_patients',methods=['get','post'])
def user_manage_patients():

	data={}
	lid=request.args['lid']
	fname=request.args['fname']
	lname=request.args['lname']
	phone=request.args['phone']
	email=request.args['email']
	hname=request.args['hname']
	place=request.args['place']
	gen=request.args['gen']
	dob=request.args['dob']
	bgroup=request.args['bgroup']
	

	q="INSERT INTO `patients` VALUES(NULL,(SELECT `user_id` FROM `user` WHERE `login_id`='%s'),'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(lid,fname,lname,phone,email,hname,place,gen,bgroup,dob)
	print(q)
	id=insert(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	return demjson.encode(data)







@api.route('/userview_dept',methods=['get','post'])
def userview_dept():
	data={}
	
	q="SELECT * FROM `department`"
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)




@api.route('/userview_doctors',methods=['get','post'])
def userview_doctors():
	data={}
	dept_id=request.args['dept_id']

	q="SELECT * FROM `doctors` where department_id='%s'"%(dept_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)




@api.route('/userview_schedule',methods=['get','post'])
def userview_schedule():
	data={}
	doc_id=request.args['doc_id']

	q="SELECT * FROM `schedule` where doctor_id='%s'"%(doc_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']	= 'userview_schedule'
	return  demjson.encode(data)




@api.route('/patient')
def patient():
	data={}
	userid=request.args['userid']
	q="SELECT * FROM `patients` WHERE user_id=(SELECT user_id FROM USER WHERE login_id='%s')"%(userid)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']	= 'patient'
	return  demjson.encode(data)
	


@api.route('/bookdoctor')
def bookdoctor():
	data={}
	userid=request.args['userid']
	patient=request.args['patient']
	schedule=request.args['schedule']

	q="insert into bookings values(null,'%s','%s',curdate(),'pending')"%(schedule,patient)
	id=insert(q)
	
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']	= 'bookdoctor'
	return  demjson.encode(data)




@api.route('/userview_bookings')
def userview_bookings():
	data={}
	lid=request.args['lid']
	q="SELECT * FROM `bookings` inner join schedule using(schedule_id) inner join doctors using(doctor_id) inner join patients using(patient_id)  WHERE user_id=(SELECT user_id FROM USER WHERE login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	return  demjson.encode(data)






@api.route('/usermakepayment')
def usermakepayment():
	data={}
	bid=request.args['bid']
	amount=request.args['amount']
	pid=request.args['pid']

	q="insert into payments values(null,'%s','%s',curdate(),'Schedule')"%(bid,amount)
	id=insert(q)
	
	if id>0:
		data['status'] = 'success'

	else:
		data['status'] = 'failed'
	data['method']	= 'usermakepayment'
	return  demjson.encode(data)





@api.route('/userview_prescription')
def userview_prescription():
	data={}
	

	q="select * from medicine_prescriptions inner join medicines using(medicine_id)"
	res=select(q)
	
	if res:
		data['status'] = 'success'
		data['data']=res
	else:
		data['status'] = 'failed'
	data['method']	= 'userview_prescription'
	return  demjson.encode(data)




@api.route('/forward_to_pharmacy')
def forward_to_pharmacy():
	data={}
	med_id=request.args['med_id']

	q="update medicine_prescriptions set status='Forward' where med_pres_id='%s'"%(med_id)
	update(q)
	
	data['status'] = 'success'
	
	data['method']	= 'forward_to_pharmacy'
	return  demjson.encode(data)












@api.route('/User_view_pets',methods=['get','post'])
def User_view_pets():
	data={}
	loginid=request.args['loginid']
	
	q="SELECT * FROM `pets` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(loginid)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='User_view_pets'
	return  demjson.encode(data)



@api.route('/user_view_doctors',methods=['get','post'])
def user_view_doctors():
	data={}
	
	q="SELECT *,concat(first_name,' ',last_name) as dname FROM `doctors`"
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_doctors'
	return  demjson.encode(data)



@api.route('/user_view_schedule',methods=['get','post'])
def user_view_schedule():
	data={}
	
	doctor_ids=request.args['doctor_ids']
	
	q="SELECT * FROM `schedule` WHERE `doctor_id`='%s'"%(doctor_ids)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_schedule'
	return  demjson.encode(data)



















@api.route('/User_pet_book_doctor',methods=['get','post'])
def User_pet_book_doctor():
	data={}
	
	login_id=request.args['login_id']
	
	q="SELECT * FROM `pets` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(login_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='User_pet_book_doctor'
	return  demjson.encode(data)














@api.route('/user_book_doctor',methods=['get','post'])
def user_book_doctor():

	data={}
	pet_id=request.args['pet_id']
	schedule_ids=request.args['schedule_ids']
	fee_amount=request.args['fee_amount']
	reason=request.args['reason']
	q="SELECT * FROM `bookings` WHERE `schedule_id`='%s'"%(schedule_ids)
	print(q)
	res1=select(q)
	if len(res1)<6:
		q= "INSERT INTO `bookings` VALUES(NULL,'%s','%s','%s',NOW(),'Paid')"%(schedule_ids,pet_id,reason)	
		bid=insert(q)
		print(q)
		q="INSERT INTO `payments` VALUES(NULL,'%s','%s',CURDATE(),'Doctor')"%(bid,fee_amount)
		id=insert(q)
		q="SELECT * FROM `doctors` INNER JOIN `schedule` USING (`doctor_id`) WHERE `schedule_id`='%s'"%(schedule_ids)
		res4=select(q)
		e_mail=res4[0]['email']
		print(e_mail)
		q="SELECT *,CONCAT(`first_name`,' ',`last_name`) AS `name` FROM `users` INNER JOIN `pets` USING (`user_id`) WHERE `pet_id` ='%s'"%(pet_id)
		res9=select(q)
		name=res9[0]['name']
		print("......",name)
		try:
			
			gmail = smtplib.SMTP('smtp.gmail.com', 587)

			gmail.ehlo()

			gmail.starttls()

			gmail.login('projectsriss2020@gmail.com','messageforall')
			print("hello")
		except Exception as e:
			print("Couldn't setup email!!"+str(e))

		msg = MIMEText("you have a new booking from " + name   )
		# msg = MIMEText("Your password is Haii")

		msg['Subject'] = 'New booking notification'

		msg['To'] = e_mail

		msg['From'] = 'projectsriss2020@gmail.com'

		try:

			gmail.send_message(msg)
			print(msg)
			print(e_mail)

		except Exception as e:

			print("COULDN'T SEND EMAIL", str(e))
		if id>0:
			data['status'] = 'success'
			

	

		
		
		else:
			data['status'] = 'failed'
	else:
		data['status'] = 'failed'
	data['method'] = 'user_book_doctor'
	return demjson.encode(data)


@api.route('/user_view_bookings',methods=['get','post'])
def user_view_bookings():
	data={}
	
	login_id=request.args['login_id']
	
	q="SELECT *,CONCAT(`doctors`.`first_name`,' ',`doctors`.`last_name`) AS dname,`schedule`.`date` AS sh_date,`bookings`.`date_time` AS booked_date FROM `bookings` INNER JOIN `schedule` USING(`schedule_id`) INNER JOIN `doctors` USING(`doctor_id`) INNER JOIN `payments` USING(`book_id`) INNER JOIN `pets` USING(`pet_id`)WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')  AND `payment_type`='Doctor' "%(login_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_bookings'
	return  demjson.encode(data)


@api.route('/user_view_medical_history',methods=['get','post'])
def user_view_medical_history():
	data={}
	
	login_id=request.args['login_id']
	
	q="SELECT *,CONCAT(`doctors`.`first_name`,' ',`doctors`.`last_name`) AS dname  FROM `medical_notes`  INNER JOIN `bookings` ON `bookings`.`book_id`=`medical_notes`.`booking_id` INNER JOIN `schedule` USING(`schedule_id`) INNER JOIN `doctors` USING(`doctor_id`) inner join pets using(pet_id) WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(login_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_medical_history'
	return  demjson.encode(data)



@api.route('/user_view_med_pres',methods=['get','post'])
def user_view_med_pres():
	data={}
	
	booking_id=request.args['booking_id']
	
	q="SELECT *,`pharmacy`.`phone` AS ph_phone,`medicine_prescriptions`.`date_time` AS pre_date_time,`medicine_prescriptions`.`status` AS pre_status FROM `medicine_prescriptions` INNER JOIN `medicines` USING(`medicine_id`) INNER JOIN `pharmacy` USING(`pharmacy_id`) WHERE `medicine_prescriptions`.`book_id`='%s'"%(booking_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_med_pres'
	return  demjson.encode(data)


@api.route('/user_forward_med_pres',methods=['get','post'])
def user_forward_med_pres():

	data={}
	med_pres_ids=request.args['med_pres_ids']

	q="UPDATE `medicine_prescriptions` SET `status`='Forward' WHERE `med_pres_id`='%s'"%(med_pres_ids)
	print(q)
	id=update(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'user_forward_med_pres'
	return demjson.encode(data)




@api.route('/user_payment',methods=['get','post'])
def user_payment():

	data={}
	action=request.args['action']
	if action=="medicine":
		pay_id=request.args['pay_id']
		med_pres_ids=request.args['med_pres_ids']

		q="UPDATE `payments` SET `payment_date`=CURDATE() WHERE pay_id='%s'"%(pay_id)
		update(q)
		q="UPDATE `medicine_prescriptions` SET `status`='Paid' WHERE `med_pres_id`='%s'"%(med_pres_ids)
		update(q)

		data['status'] = 'success'
	else:
		schedule_id=request.args['schedule_id']
		login_id=request.args['login_id']
		pamount=request.args['pamount']

		q="insert into `bookings` values(null,'%s',(select `patient_id` from `patients` where `login_id`='%s'),curdate(),'book')"%(schedule_id,login_id)
		book_id=insert(q)
		print(q)
		q="insert into `payments` values(null,'%s','%s',curdate(),'doctor')"%(boo_id,pamount)
		insert(q)
		data['status'] = 'success'
	
	data['method'] = 'user_payment'
	return demjson.encode(data)


@api.route('/user_view_test_pres',methods=['get','post'])
def user_view_test_pres():
	data={}
	
	booking_id=request.args['booking_id']
	
	q="SELECT *,`laboratory`.`phone` AS lphone,`test_prescription`.`date_time` AS test_date,`test_prescription`.`status` AS test_status FROM `test_prescription` INNER JOIN `lab_tests` ON `lab_test_id`=`test_id` INNER JOIN `laboratory` USING(`lab_id`) WHERE `test_prescription`.`book_id`='%s'"%(booking_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_test_pres'
	return  demjson.encode(data)




@api.route('/user_forward_test_pres',methods=['get','post'])
def user_forward_test_pres():

	data={}
	test_pres_ids=request.args['test_pres_ids']

	q="UPDATE `test_prescription` SET `status`='Forward' WHERE `test_pres_id`='%s'"%(test_pres_ids)
	id=update(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'user_forward_test_pres'
	return demjson.encode(data)



@api.route('/user_view_med_payment',methods=['get','post'])
def user_view_med_payment():
	data={}
	
	book_id=request.args['book_id']
	
	q="SELECT * FROM `payments` WHERE `book_id`='%s' AND `payment_type`='Medicne'"%(book_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['data'] = res[0]['amount']
		data['data1'] = res[0]['pay_id']
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_med_payment'
	return  demjson.encode(data)





@api.route('/user_view_test_payment',methods=['get','post'])
def user_view_test_payment():
	data={}
	
	book_id=request.args['book_id']
	
	q="SELECT * FROM `payments` WHERE `book_id`='%s' and payment_type='Lab' "%(book_id)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'
		data['amount'] = res[0]['amount']
		data['pay_id']= res[0]['pay_id']
		
	else:
		data['status']	= 'failed'
	data['method']='user_view_test_payment'
	return  demjson.encode(data)



@api.route('/user_test_payment',methods=['get','post'])
def user_test_payment():

	data={}
	pay_id=request.args['pay_id']
	test_pres_ids=request.args['test_pres_ids']

	q="UPDATE `payments` SET `payment_date`=CURDATE() WHERE `pay_id`='%s'"%(pay_id)
	update(q)
	q="UPDATE`test_prescription` SET `status`='Paid' WHERE `test_pres_id`='%s'"%(test_pres_ids)
	update(q)
	
	data['status'] = 'success'
	
	data['method'] = 'user_test_payment'
	return demjson.encode(data)
 
 
 
 
@api.route('/chat', methods=['get', 'post'])
def chat():
    data = {}
    sender_id = request.args['sender_id']
    receiver_id = request.args['receiver_id']
    details = request.args['details']

    q2="INSERT INTO chat(sender_id,sender_type,receiver_id,receiver_type,message,`date_time`)VALUES('%s','user',(SELECT `login_id` FROM `doctors` WHERE `doctor_id`='%s'),'doctor','%s',NOW())"%(sender_id,receiver_id,details)
    id=insert(q2)
    if id > 0:
        data['status'] = 'success'

    else:
        data['status'] = 'failed'
    data['method'] = 'chat'
    return demjson.encode(data)


@api.route('/chatdetail', methods=['get', 'post'])
def chatdetail():
    data = {}

    sender_id = request.args['sender_id']
    receiver_id = request.args['receiver_id']


    q = "SELECT * FROM `chat` WHERE (`sender_id`='%s' AND `receiver_id`=(SELECT `login_id` FROM `doctors` WHERE `doctor_id`='%s')) OR (`sender_id`=(SELECT `login_id` FROM `doctors` WHERE `doctor_id`='%s') AND `receiver_id`='%s')"%(sender_id,receiver_id,receiver_id,sender_id)
    print(q)
    res = select(q)
    if res:
        data['status'] = 'success'
        data['data'] = res
    else:
        data['status'] = 'failed'
    data['method'] = 'chatdetail'
    return demjson.encode(data)





@api.route('/userview_patients',methods=['get','post'])
def userview_patients():
	data={}
	
	lid=request.args['lid']
	
	q="SELECT * FROM `patients` WHERE `user_id`=(select user_id from user where login_id='%s') "%(lid)
	print(q)
	res = select(q)
	print(res)
	if res :
		data['status']  = 'success'

		data['data'] = res
		
	else:
		data['status']	= 'failed'
	data['method']='userview_patients'
	return  demjson.encode(data)




@api.route('/user_add_physical_conditions',methods=['get','post'])
def user_add_physical_conditions():

	data={}
	pid=request.args['pid']
	bp=request.args['bp']
	sugar=request.args['sugar']
	cholestrol=request.args['cholestrol']
	height=request.args['height']
	weight=request.args['weight'] 

	
	q="insert into `physical_conditions` values(null,'%s','%s','%s','%s','%s','%s',curdate())"%(pid,bp,sugar,cholestrol,height,weight)
	id=insert(q)
	if id>0:
		data['status'] = 'success'
		
	else:
		data['status'] = 'failed'
	data['method'] = 'user_add_physical_conditions'
	return demjson.encode(data)


@api.route('/predictdisease',methods=['get','post'])
def predictdisease():

	data={}
	ss=[]
	sss=[]
	fo=request.args['a']
	ss.append(fo)
	fhi=request.args['b']
	ss.append(fhi)
	flo=request.args['c']
	ss.append(flo)
	mdvp=request.args['d']
	ss.append(mdvp)
	jabs=request.args['e']
	ss.append(jabs)
	rap=request.args['f']
	ss.append(rap)
	# mppq=request.args['g']
	# ss.append(mppq)
	# jddp=request.args['h']
	# ss.append(jddp)
	# shim=request.args['i']
	# ss.append(shim)
	# db=request.args['j']
	# ss.append(db)
	# shm=request.args['k']
	# ss.append(shm)
	# apq=request.args['l']
	# ss.append(apq)
	# ap=request.args['m']
	# ss.append(ap)
	# dda=request.args['n']
	# ss.append(dda)
	# nhr=request.args['o']
	# ss.append(nhr)
	# hnr=request.args['p']
	# ss.append(nhr)
	# rpde=request.args['q']
	# ss.append(rpde)
	# d=request.args['r']
	# ss.append(d)
	# dfa=request.args['s']
	# ss.append(dfa)
	# spread1=request.args['t']
	# ss.append(spread1)
	# spresd2=request.args['u']
	# ss.append(spresd2)
	# ppe=request.args['w']
	# ss.append(ppe)
	# print(fo,fhi,mdvp,jabs,rap,mppq,jddq,shim,db,shm,apq,ap,dda,nhr,hnr,rpde,d,dfa,spread1,spresd2,ppe)
	sss.append(ss)
	print(sss)
	out=predict(sss)
	print(out)
	if out[0]==1:
		outs="Parkinson"
	elif out[0]==0:
		outs="Not Parkinson"
	if int(fo) < 4 and int(flo) < 3:
		outs="Not Parkinson"



	data['data']=outs
		

	data['status'] = 'success'
	data['method'] = 'predictdisease'
	return demjson.encode(data)
