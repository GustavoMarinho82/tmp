# FUNÇÕES

def obterValorPeso(item):
	# Retorna o valor por peso do item
	return item["valor"] / item["peso"]

def intercalarValorPeso(lista1, lista2):
	# Função auxiliar de mergeSortValorPeso, retorna uma lista com os elementos de lista1 e lista2 intercalados
	listaInter = []
	i = j = 0

	while (i < len(lista1)) and (j < len(lista2)):
		if (obterValorPeso(lista1[i]) > obterValorPeso(lista2[j])):
			listaInter.append(lista1[i])
			i += 1
			
		else:
			listaInter.append(lista2[j])
			j += 1

	listaInter.extend(lista1[i:])
	listaInter.extend(lista2[j:])
	
	return listaInter

def mergeSortValorPeso(itens):
	# Retorna uma cópia de itens ordenada decrescentemente baseando-se no valor por peso de cada item
	sublistas = [[item] for item in itens]

	while (len(sublistas) > 1):
		novaLista = []

		for i in range(0, len(sublistas), 2):
			if (i + 1 < len(sublistas)):
				novaLista.append(intercalarValorPeso(sublistas[i], sublistas[i+1]))
			
			else:
				novaLista.append(sublistas[i])

		sublistas = novaLista

	return sublistas[0] if sublistas else []

def mochilaFracionaria(itens, pesoMax):
	# Imprime a configuração de itens que possui o maior valor total, respeitando o peso máximo
	pesoRestante = pesoMax
	valorTotal = 0
	
	# Ordena decrescentemente a cópia da lista de itens baseando-se no valor por peso de cada item
	itensCp = mergeSortValorPeso(itens)

	print("Melhor configuração de itens para o peso máximo de {}: ".format(pesoMax))
	
	for item in itensCp:
		if (item["peso"] <= pesoRestante):
			valorTotal += item["valor"]
			pesoRestante -= item["peso"]
			
			print(" {} - Fracao: 1 | Valor: {} | Peso: {}".format(item["nome"], item["valor"], item["peso"]));

		elif (pesoRestante > 0): # Essa condição serve para ignorar pesos negativos e para formatação do output 
			fracao = pesoRestante / item["peso"]
			valorTotal += item["valor"] * fracao
			
			print(" {} - Fracao: {:.2f} | Valor: {:.2f} | Peso: {:.2f} | Valor original: {} | Peso original: {}".format(item["nome"], fracao, item["valor"] * fracao, item["peso"] * fracao, item["valor"], item["peso"]));
			break;

	print("\nValor total: {:.2f}".format(valorTotal))

def imprimirItens(itens):
	# Imprime os dados dos itens
	print("Itens: ")
	
	for item in itens:
		print(" {} - Valor: {} | Peso: {} | Valor/peso: {:.2f}".format(item["nome"], item["valor"], item["peso"], item["valor"] / item["peso"]))


# PROGRAMA PRINCIPAL

itens = [
    {"nome": "item1", "valor": 11, "peso": 10},
    {"nome": "item2", "valor": 15, "peso": 12},
    {"nome": "item3", "valor": 7,  "peso": 6},
    {"nome": "item4", "valor": 8,  "peso": 5},
    {"nome": "item5", "valor": 6,  "peso": 4}
]

imprimirItens(itens)
print()
mochilaFracionaria(itens, 25)
